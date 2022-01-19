/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 12:06
 */
package net.lizhaoweb.common.file.download;

import lombok.Setter;
import net.lizhaoweb.common.file.event.ProgressEventType;
import net.lizhaoweb.common.file.event.ProgressListener;
import net.lizhaoweb.common.file.event.ProgressPublisher;
import net.lizhaoweb.common.file.utils.CodingUtils;
import net.lizhaoweb.common.file.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 通过HTTP协议下载
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class FileDownloadOperator implements IFileDownloadOperator {

    @Setter
    private IFileObjectOperator fileObjectOperator;


    public static void createFixedFile(String filePath, long length) throws IOException {
        File file = new File(filePath);
        RandomAccessFile rf = null;

        try {
            rf = new RandomAccessFile(file, "rw");
            rf.setLength(length);
        } finally {
//            if (rf != null) {
//                rf.close();
//            }
            IOUtils.close(rf);
        }
    }

    @Override
    public DownloadFileResult downloadFile(DownloadFileRequest downloadFileRequest) throws Throwable {
        CodingUtils.assertParameterNotNull(downloadFileRequest, "downloadFileRequest");

        String uri = downloadFileRequest.getUri();
        String queryString = downloadFileRequest.getQueryString();

        CodingUtils.assertParameterNotNull(queryString, "queryString");
        CodingUtils.assertParameterNotNull(uri, "uri");

        // the download file name is not specified, using the uri as the local file name.
        if (downloadFileRequest.getDownloadFile() == null) {
            downloadFileRequest.setDownloadFile(uri);
        }

        // the checkpoint is enabled, but no checkpoint file, using the default checkpoint file name.
        if (downloadFileRequest.isEnableCheckpoint()) {
            if (downloadFileRequest.getCheckpointFile() == null || downloadFileRequest.getCheckpointFile().isEmpty()) {
                downloadFileRequest.setCheckpointFile(downloadFileRequest.getDownloadFile() + ".dcp");
            }
        }

        return downloadFileWithCheckpoint(downloadFileRequest);
    }

    private DownloadFileResult downloadFileWithCheckpoint(DownloadFileRequest downloadFileRequest) throws Throwable {
        DownloadFileResult downloadFileResult = new DownloadFileResult();
        DownloadCheckPoint downloadCheckPoint = new DownloadCheckPoint();

        // The checkpoint is enabled, downloads the parts download results from checkpoint file.
        if (downloadFileRequest.isEnableCheckpoint()) {
            // read the last download result. If checkpoint file dosx not exist, or the file is updated/corrupted, re-download again.
            try {
                downloadCheckPoint.load(downloadFileRequest.getCheckpointFile());
            } catch (Exception e) {
                remove(downloadFileRequest.getCheckpointFile());
            }

            // The download checkpoint is corrupted, download again.
            if (!downloadCheckPoint.isValid(fileObjectOperator)) {
                prepare(downloadCheckPoint, downloadFileRequest);
                remove(downloadFileRequest.getCheckpointFile());
            }
        } else {
            // The checkpoint is not enabled, download the file again.
            prepare(downloadCheckPoint, downloadFileRequest);
        }

        // Progress listen starts tracking the progress.
        ProgressListener listener = downloadFileRequest.getProgressListener();
        ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_STARTED_EVENT);

        // Concurrently download parts.
        DownloadResult downloadResult = download(downloadCheckPoint, downloadFileRequest);
//        Long serverCRC = null;
        for (PartResult partResult : downloadResult.getPartResults()) {
//            if (partResult.getServerCRC() != null) {
//                serverCRC = partResult.getServerCRC();
//            }
            if (partResult.isFailed()) {
                ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_PART_FAILED_EVENT);
                throw partResult.getException();
            }
        }
//
//        // check crc64
//        if (fileObjectOperator.getInnerClient().getClientConfiguration().isCrcCheckEnabled()) {
//            Long clientCRC = calcObjectCRCFromParts(downloadResult.getPartResults());
//            try {
//                OSSUtils.checkChecksum(clientCRC, serverCRC, downloadResult.getObjectMetadata().getRequestId());
//            } catch (Exception e) {
//                ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_FAILED_EVENT);
//                throw new InconsistentException(clientCRC, serverCRC, downloadResult.getObjectMetadata().getRequestId());
//            }
//        }


        // Publish the complete status.
        ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_COMPLETED_EVENT);

        // rename the temp file.
        renameTo(downloadFileRequest.getTempDownloadFile(), downloadFileRequest.getDownloadFile());

        // The checkpoint is enabled, delete the checkpoint file after a successful download.
        if (downloadFileRequest.isEnableCheckpoint()) {
            remove(downloadFileRequest.getCheckpointFile());
        }

        downloadFileResult.setObjectMetadata(downloadResult.getObjectMetadata());
        return downloadFileResult;
    }

    private void prepare(DownloadCheckPoint downloadCheckPoint, DownloadFileRequest downloadFileRequest) throws IOException {
        downloadCheckPoint.magic = DownloadCheckPoint.DOWNLOAD_MAGIC;
        downloadCheckPoint.downloadFile = downloadFileRequest.getDownloadFile();
        downloadCheckPoint.uri = downloadFileRequest.getUri();
        downloadCheckPoint.queryString = downloadFileRequest.getQueryString();
        downloadCheckPoint.objectStat = ObjectStat.getFileStat(fileObjectOperator, downloadCheckPoint.uri, downloadCheckPoint.queryString);
        downloadCheckPoint.downloadParts = splitFile(downloadCheckPoint.objectStat.size, downloadFileRequest.getPartSize());

        if (downloadCheckPoint.downloadParts != null && downloadCheckPoint.downloadParts.size() > downloadFileRequest.getTaskNum()) {
            downloadFileRequest.setTaskNum(downloadCheckPoint.downloadParts.size());
        }

        createFixedFile(downloadFileRequest.getTempDownloadFile(), downloadCheckPoint.objectStat.size);
    }

    private DownloadResult download(DownloadCheckPoint downloadCheckPoint, DownloadFileRequest downloadFileRequest) throws Throwable {
        DownloadResult downloadResult = new DownloadResult();
        ArrayList<PartResult> taskResults = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(downloadFileRequest.getTaskNum());
        ArrayList<Future<PartResult>> futures = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        ProgressListener listener = downloadFileRequest.getProgressListener();

        // Compute the size of data pending download.
        long contentLength = 0;
        for (int i = 0; i < downloadCheckPoint.downloadParts.size(); i++) {
            if (!downloadCheckPoint.downloadParts.get(i).isCompleted) {
                long partSize = downloadCheckPoint.downloadParts.get(i).end - downloadCheckPoint.downloadParts.get(i).start + 1;
                contentLength += partSize;
            }
        }
        ProgressPublisher.publishResponseContentLength(listener, contentLength);
        downloadFileRequest.setProgressListener(null);

        // Concurrently download parts.
        for (int i = 0; i < downloadCheckPoint.downloadParts.size(); i++) {
            if (!downloadCheckPoint.downloadParts.get(i).isCompleted) {
                Task task = new Task(i, "download-" + i, downloadCheckPoint, i, downloadFileRequest, fileObjectOperator, listener);
                futures.add(service.submit(task));
                tasks.add(task);
            } else {
                taskResults.add(new PartResult(i + 1, downloadCheckPoint.downloadParts.get(i).start, downloadCheckPoint.downloadParts.get(i).end, downloadCheckPoint.downloadParts.get(i).length));
            }
        }
        service.shutdown();

        // Waiting for all parts download,
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        for (Future<PartResult> future : futures) {
            try {
                PartResult tr = future.get();
                taskResults.add(tr);
            } catch (ExecutionException e) {
                downloadFileRequest.setProgressListener(listener);
                throw e.getCause();
            }
        }

        // Sorts the download result by the part number.
        Collections.sort(taskResults, new Comparator<PartResult>() {
            @Override
            public int compare(PartResult p1, PartResult p2) {
                return p1.getNumber() - p2.getNumber();
            }
        });

        // sets the return value.
        downloadResult.setPartResults(taskResults);
        if (tasks.size() > 0) {
            downloadResult.setObjectMetadata(tasks.get(0).getObjectMetadata());
        }
        downloadFileRequest.setProgressListener(listener);

        return downloadResult;
    }

    private ArrayList<DownloadPart> splitFile(long objectSize, long partSize) {
        ArrayList<DownloadPart> parts = new ArrayList<>();

        long partNum = objectSize / partSize;
        if (partNum >= 10000) {
            partSize = objectSize / (10000 - 1);
        }

        long offset = 0L;
        for (int i = 0; offset < objectSize; offset += partSize, i++) {
            DownloadPart part = new DownloadPart();
            part.index = i;
            part.start = offset;
            part.end = getPartEnd(offset, objectSize, partSize);
            parts.add(part);
        }

        return parts;
    }

    private long getPartEnd(long begin, long total, long per) {
        if (begin + per > total) {
            return total - 1;
        }
        return begin + per - 1;
    }

    private boolean remove(String filePath) {
        boolean flag = false;
        File file = new File(filePath);

        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }

        return flag;
    }

    private static void renameTo(String srcFilePath, String destFilePath) throws IOException {
        File srcfile = new File(srcFilePath);
        File destfile = new File(destFilePath);
        moveFile(srcfile, destfile);
    }

    private static void moveFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        if (destFile.exists()) {
            if (!destFile.delete()) {
                throw new IOException("Failed to delete original file '" + srcFile + "'");
            }
        }

        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            IOUtils.copy(is, os);
//            byte[] buffer = new byte[4096];
//            int length;
//            while ((length = is.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
        } finally {
            IOUtils.close(is);
            IOUtils.close(os);
        }
    }
}
