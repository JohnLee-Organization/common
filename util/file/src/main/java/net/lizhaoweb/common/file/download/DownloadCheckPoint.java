/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:52
 */
package net.lizhaoweb.common.file.download;

import net.lizhaoweb.common.file.GenericRequest;
import net.lizhaoweb.common.file.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;

/**
 * 下载检查点
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class DownloadCheckPoint implements Serializable {

    static final String DOWNLOAD_MAGIC = "92611BED-89E2-46B6-89E5-72F273D4B0A3";

    public String magic; // magic
    public int md5; // the md5 of checkpoint data.
    public String downloadFile; // local path for the download.
    public String uri; //
    public String queryString; //
    public ObjectStat objectStat; // object state
    public ArrayList<DownloadPart> downloadParts; // download parts list.

    /**
     * Loads the checkpoint data from the checkpoint file.
     */
    public synchronized void load(String cpFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(cpFile);
            in = new ObjectInputStream(fileIn);
            DownloadCheckPoint dcp = (DownloadCheckPoint) in.readObject();
            assign(dcp);
        } finally {
            IOUtils.close(in);
            IOUtils.close(fileIn);
        }
//        FileInputStream fileIn = new FileInputStream(cpFile);
//        ObjectInputStream in = new ObjectInputStream(fileIn);
//        DownloadCheckPoint dcp = (DownloadCheckPoint) in.readObject();
//        assign(dcp);
//        in.close();
//        fileIn.close();
    }

    /**
     * Writes the checkpoint data to the checkpoint file.
     */
    public synchronized void dump(String cpFile) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream outStream = null;
        try {
            this.md5 = hashCode();
            fileOut = new FileOutputStream(cpFile);
            outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(this);
            outStream.flush();
        } finally {
//            if (outStream != null) {
//                outStream.close();
//            }
//            if (fileOut != null) {
//                fileOut.close();
//            }
            IOUtils.close(outStream);
            IOUtils.close(fileOut);
        }
    }

    /**
     * Updates the part's download status.
     *
     * @throws IOException
     */
    public synchronized void update(int index, boolean completed) throws IOException {
        downloadParts.get(index).isCompleted = completed;
    }

    /**
     * Check if the object matches the checkpoint information.
     */
    public synchronized boolean isValid(IFileObjectOperator fileObjectOperator) {
        // 比较checkpoint的magic和md5
        if (this.magic == null || !this.magic.equals(DOWNLOAD_MAGIC) || this.md5 != hashCode()) {
            return false;
        }

        GenericRequest genericRequest = new GenericRequest(this.uri, this.queryString);
        FileObjectMetadata meta = fileObjectOperator.getFileObjectMeta(genericRequest);

        // Object's size, last modified time or ETAG are not same as the one
        // in the checkpoint.
        if (this.objectStat.size != meta.getSize() || !this.objectStat.lastModified.equals(meta.getLastModified())) {
            return false;
        }

        return true;
    }

    private void assign(DownloadCheckPoint dcp) {
        this.magic = dcp.magic;
        this.md5 = dcp.md5;
        this.downloadFile = dcp.downloadFile;
        this.uri = dcp.uri;
        this.queryString = dcp.queryString;
        this.objectStat = dcp.objectStat;
        this.downloadParts = dcp.downloadParts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        result = prime * result + ((downloadFile == null) ? 0 : downloadFile.hashCode());
        result = prime * result + ((magic == null) ? 0 : magic.hashCode());
        result = prime * result + ((queryString == null) ? 0 : queryString.hashCode());
        result = prime * result + ((objectStat == null) ? 0 : objectStat.hashCode());
        result = prime * result + ((downloadParts == null) ? 0 : downloadParts.hashCode());
        return result;
    }
}
