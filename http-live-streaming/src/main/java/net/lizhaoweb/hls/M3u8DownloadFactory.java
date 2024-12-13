/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-12
 * @time : 19:41
 */
package net.lizhaoweb.hls;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;
import java.util.concurrent.*;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 下载工厂
 * <p>
 * Created by Jhon on 2024/12/12 19:41
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@SuppressWarnings({"unused"})
public class M3u8DownloadFactory {

    private static volatile M3u8Downloader downloader;

    /**
     * 解决java不支持AES/CBC/PKCS7Padding模式解密
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 获取实例
     *
     * @param downloadUrl 要下载的链接
     * @return 返回m3u8下载实例
     */
    public static M3u8Downloader getInstance(String downloadUrl) {
        if (downloader == null) {
            synchronized (M3u8Downloader.class) {
                if (downloader == null) downloader = new M3u8Downloader(downloadUrl);
            }
        }
        return downloader;
    }

    public static void destroy() {
        downloader = null;
    }

    public static void doNothing() {
    }

    public static enum DownloadTsType {
        DECRYPTED, // 已解密
        CONTINUE, // 结束本次循环，继续
        VOID, // 无返回值
        DEL_FILE_FAIL, // 删除文件失败
        SUCCESS, // 成功
        RETRY_MAX_COUNT, // 超过最大重试次数
        DECRYPT_FAIL, // 解密失败
        INTERRUPTED, // 中断
        ;
    }

    @Slf4j
    public static class M3u8Downloader {

        // 下载后未解密的文件名后缀
        private static final String DOWNLOADED_FILE_SUFFIX = ".xy";

        // 解密后的文件名后缀
        private static final String DECODE_FILE_SUFFIX = ".xyz";

        // 合并后的文件名后缀
        private static final String FINAL_FILE_SUFFIX = ".mp4";

        //优化内存占用
        private static final BlockingQueue<byte[]> BLOCKING_QUEUE = new LinkedBlockingQueue<>();

        //要下载的m3u8链接
        @Getter
        private final String downloadUrl;

        //线程数
        @Getter
        private int threadCount = 1;

        //重试次数
        @Setter
        @Getter
        private int retryCount = 30;

        //链接连接超时时间（单位：毫秒）
        @Setter
        @Getter
        private long timeoutMillisecond = 1000L;

        //合并后的文件存储目录
        @Setter
        @Getter
        private String dir;

        //合并后的视频文件名称
        @Setter
        @Getter
        private String filename;

        //已完成ts片段个数
        @Getter
        private int finishedCount = 0;

        //解密算法名称
        private String method;

        //密钥
        private String key = "";

        //密钥字节
        private byte[] keyBytes = new byte[16];

        //key是否为字节
        private boolean isByte = false;

        //IV
        private String iv = "";

        //所有ts片段下载链接
        private Set<String> tsSet = new LinkedHashSet<>();

        //解密后的片段
        private Set<File> finishedFiles = new ConcurrentSkipListSet<>(Comparator.comparingInt(o -> Integer.parseInt(o.getName().replace(DECODE_FILE_SUFFIX, ""))));

        //已经下载的文件大小
        private volatile long downloadBytes = 0L;

        //监听间隔
        @Setter
        private volatile long interval = 0L;

        //自定义请求头
        @Getter
        private Map<String, Object> requestHeaderMap = new HashMap<>();

        //监听事件
        private Set<DownloadListener> listenerSet = new HashSet<>(5);

        //监听任务事件
        private Set<DownloadTaskListener> taskListenerSet = new HashSet<>();

        //代理设置
        @Getter
        private Proxy proxy;

        @Setter
        private boolean zeroCopy;


        private M3u8Downloader(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            requestHeaderMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
        }

        public void setThreadCount(int threadCount) {
            if (BLOCKING_QUEUE.size() < threadCount) {
                for (int i = BLOCKING_QUEUE.size(); i < threadCount * Constant.FACTOR; i++) {
                    try {
                        BLOCKING_QUEUE.put(new byte[Constant.BYTE_COUNT]);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            this.threadCount = threadCount;
        }

        public void setProxy(int port) {
            this.setProxy(Proxy.Type.HTTP, "127.0.0.1", port);
        }

        public void setProxy(String address, int port) {
            this.setProxy(Proxy.Type.HTTP, address, port);
        }

        public void setProxy(Proxy.Type type, String address, int port) {
            this.proxy = new Proxy(type, new InetSocketAddress(address, port));
        }

        public M3u8Downloader withProxy(int port) {
            this.setProxy(port);
            return this;
        }

        public M3u8Downloader withProxy(String address, int port) {
            this.setProxy(address, port);
            return this;
        }

        public M3u8Downloader withProxy(Proxy.Type type, String address, int port) {
            this.setProxy(Proxy.Type.HTTP, address, port);
            return this;
        }

        public M3u8Downloader addRequestHeaderMap(Map<String, Object> requestHeaderMap) {
            this.requestHeaderMap.putAll(requestHeaderMap);
            return this;
        }

        public M3u8Downloader addRequestHeader(String Key, Object value) {
            this.requestHeaderMap.put(key, value);
            return this;
        }

        /**
         * 增加下载情况监听器
         *
         * @param listener 下载情况监听器
         * @return M3u8Downloader
         */
        public M3u8Downloader addListener(DownloadListener listener) {
            listenerSet.add(listener);
            return this;
        }

        /**
         * 增加下载任务监听器
         *
         * @param listener 下载任务监听器
         * @return M3u8Downloader
         */
        public M3u8Downloader addListener(DownloadTaskListener listener) {
            taskListenerSet.add(listener);
            return this;
        }

        public M3u8Downloader savePath(String savePath) {
            this.setDir(savePath);
            return this;

        }

        public M3u8Downloader filename(String filename) {
            this.setFilename(filename);
            return this;
        }

        public M3u8Downloader retry(int count) {
            this.setRetryCount(count);
            return this;
        }

        public M3u8Downloader threadSize(int threadCount) {
            this.setThreadCount(threadCount);
            return this;
        }

        public M3u8Downloader timeout(long timeoutMillisecond) {
            this.setTimeoutMillisecond(timeoutMillisecond);
            return this;
        }

        public M3u8Downloader timeout(long timeout, TimeUnit timeUnit) {
            return this.timeout(timeUnit.toMillis(timeout));
        }

        public M3u8Downloader listenInterval(long listenIntervalMillisecond) {
            this.setInterval(listenIntervalMillisecond);
            return this;
        }

        public M3u8Downloader listenInterval(long timeout, TimeUnit timeUnit) {
            return this.listenInterval(timeUnit.toMillis(timeout));
        }

        public M3u8Downloader withZeroCopy() {
            zeroCopy = true;
            return this;
        }


        /**
         * 开始下载视频
         */
        public void start() {
            checkField();
            String tsUrl = getTsUrl();
            if (StringUtils.isEmpty(tsUrl)) {
                if (log.isInfoEnabled()) log.info("不需要解密");
            }
            download();
        }

        /**
         * 下载视频
         */
        private void download() {
            //线程池
            final ExecutorService downloadThreadPool = new ThreadPoolExecutor(threadCount, threadCount, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10000), r -> {
                Thread thread = new Thread(r);
                thread.setName("DOWNLOAD-TS-THREAD-" + thread.getId());
                return thread;
            }, new ThreadPoolExecutor.AbortPolicy());
            int downloadIndex = 0;
            //如果生成目录不存在，则创建
            File savePathRoot = new File(dir);
            if (!savePathRoot.exists()) {
                if (!savePathRoot.mkdirs()) throw new M3u8Exception("Failed to make dir: " + savePathRoot);
            }
            int len = tsSet.size();
            List<Future<M3u8DownloadFactory.DownloadTsType>> taskList = new ArrayList<>();
            //执行多线程下载
            for (String tsFilename : tsSet) {
                downloadIndex++;
                Future<M3u8DownloadFactory.DownloadTsType> taskResult = downloadThreadPool.submit(getTask(tsFilename, downloadIndex, len));
                taskList.add(taskResult);
            }
            startListener(downloadThreadPool);
            downloadThreadPool.shutdown();

            for (Future<M3u8DownloadFactory.DownloadTsType> f : taskList) {
                try {
                    if (M3u8DownloadFactory.DownloadTsType.DEL_FILE_FAIL == f.get()) {
                        doNothing();
                    } else if (M3u8DownloadFactory.DownloadTsType.INTERRUPTED == f.get()) {
                        doNothing();
                    } else if (M3u8DownloadFactory.DownloadTsType.DECRYPT_FAIL == f.get()) {
                        doNothing();
                    } else if (M3u8DownloadFactory.DownloadTsType.RETRY_MAX_COUNT == f.get()) {
                        doNothing();
                    } else if (M3u8DownloadFactory.DownloadTsType.SUCCESS == f.get()) {
                        doNothing();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (log.isInfoEnabled()) log.info("下载完成，正在合并文件！共{}个！{}", finishedFiles.size(), StringUtils.convertToDownloadSpeed(new BigDecimal(downloadBytes), 3));
            mergeTs(); //开始合并视频
            deleteFiles(); //删除多余的ts片段
            if (log.isInfoEnabled()) log.info("视频合并完成，欢迎使用!");
        }

        private void startListener(ExecutorService fixedThreadPool) {
            new Thread(() -> {
                for (DownloadListener downloadListener : listenerSet) {
                    try {
                        if (downloadListener == null) continue;
                        downloadListener.onStart(new DownloadListener.AbstractDownloadStartEvent() {
                            @Override
                            public String getDownloadUrl() {
                                return downloadUrl;
                            }
                        });
                    } catch (Throwable e) {
                        log.warn("Listen on start: " + e.getMessage(), e);
                    }
                }
                //轮询是否下载成功
                while (!fixedThreadPool.isTerminated()) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        break;
                    }
                    for (DownloadListener downloadListener : listenerSet) {
                        try {
                            if (downloadListener == null) continue;
                            downloadListener.process(new DownloadListener.AbstractDownloadProcessEvent() {
                                @Override
                                public String getDownloadUrl() {
                                    return downloadUrl;
                                }

                                @Override
                                public int getFinished() {
                                    return finishedCount;
                                }

                                @Override
                                public int getTotal() {
                                    return tsSet.size();
                                }

                                @Override
                                public float getPercent() {
                                    return finishedPercent().floatValue();
                                }
                            });
                        } catch (Throwable e) {
                            log.warn("Listen on process: " + e.getMessage(), e);
                        }
                    }
                }
                for (DownloadListener downloadListener : listenerSet) {
                    try {
                        if (downloadListener == null) continue;
                        downloadListener.onEnd(new DownloadListener.AbstractDownloadEndEvent() {
                            @Override
                            public String getDownloadUrl() {
                                return downloadUrl;
                            }
                        });
                    } catch (Throwable e) {
                        log.warn("Listen on end: " + e.getMessage(), e);
                    }
                }
            }, "DOWNLOAD-LISTENER-THREAD").start();
            new Thread(() -> {
                while (!fixedThreadPool.isTerminated()) {
                    BigDecimal startSize = new BigDecimal(downloadBytes);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        break;
                    }
                    try {
                        for (DownloadListener downloadListener : listenerSet) {
                            if (downloadListener == null) continue;
                            downloadListener.speed(new DownloadListener.AbstractDownloadSpeedEvent() {
                                @Override
                                public String getDownloadUrl() {
                                    return downloadUrl;
                                }

                                @Override
                                public String speed() {
                                    BigDecimal nowSize = new BigDecimal(downloadBytes);
                                    return StringUtils.convertToDownloadSpeed(nowSize.subtract(startSize), 3) + "/s";
                                }
                            });
                        }
                    } catch (Throwable e) {
                        log.warn("Listen on speed: " + e.getMessage(), e);
                    }
                }
            }, "SPEED-LISTENER-THREAD").start();
        }

        private BigDecimal finishedPercent() {
            BigDecimal finished = new BigDecimal(finishedCount);
            BigDecimal total = new BigDecimal(tsSet.size());
            BigDecimal percent = new BigDecimal(100);
            return finished.divide(total, 4, RoundingMode.HALF_UP).multiply(percent).setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * 合并下载好的ts片段
         */
        private void mergeTs() {
            try {
                File videoFile = new File(dir, filename + FINAL_FILE_SUFFIX);
                System.gc();
                if (videoFile.exists()) {
                    if (!videoFile.delete()) throw new M3u8Exception("Failed to delete file: " + videoFile);
                } else {
                    if (!videoFile.createNewFile()) throw new M3u8Exception("Failed to create file: " + videoFile);
                }
                if (zeroCopy) {
                    this.mergeVideoFileByZeroCopy(videoFile);
                } else {
                    this.mergeVideoFileByStream(videoFile);
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) log.error(e.getMessage(), e);
            }
        }

        // 通过零白拷贝合并文件
        private void mergeVideoFileByZeroCopy(File videoFile) throws IOException {
            FileOutputStream outputStream = null;
            FileInputStream inputStream = null;
            try {
                outputStream = new FileOutputStream(videoFile);
                FileChannel outChannel = outputStream.getChannel();
                long offset = 0;
                for (File file : finishedFiles) {
                    try {
                        int len;
                        inputStream = new FileInputStream(file);
                        FileChannel inChannel = inputStream.getChannel();
                        offset += inChannel.transferTo(offset, inChannel.size(), outChannel);
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }
            } finally {
                IOUtils.closeQuietly(outputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }

        // 通过流合并文件
        private void mergeVideoFileByStream(File videoFile) throws IOException {
            FileOutputStream outputStream = null;
            FileInputStream inputStream = null;
            try {
                outputStream = new FileOutputStream(videoFile);
                byte[] buff = new byte[4096];
                for (File file : finishedFiles) {
                    try {
                        int len;
                        inputStream = new FileInputStream(file);
                        while ((len = inputStream.read(buff)) != -1) {
                            outputStream.write(buff, 0, len);
                        }
                        outputStream.flush();
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }
            } finally {
                IOUtils.closeQuietly(outputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }

        /**
         * 删除下载好的片段
         */
        private void deleteFiles() {
            File dirFile = new File(dir);
            for (File delFile : Objects.requireNonNull(dirFile.listFiles(f -> f.getName().endsWith(DOWNLOADED_FILE_SUFFIX) || f.getName().endsWith(DECODE_FILE_SUFFIX)))) {
                if (!delFile.delete()) {
                    if (log.isWarnEnabled()) log.warn("文件{}删除失败", delFile);
                }
            }
        }

        /**
         * 开启下载线程
         *
         * @param tsUrl ts片段链接
         * @param index ts片段序号
         * @param total ts片段总数
         * @return 线程
         */
        private Callable<M3u8DownloadFactory.DownloadTsType> getTask(String tsUrl, int index, int total) {
            return () -> {
                for (DownloadTaskListener taskListener : taskListenerSet) {
                    try {
                        if (taskListener == null) continue;
                        taskListener.onStart(new DownloadTaskListener.AbstractDownloadStartEvent() {
                            @Override
                            public String getDownloadUrl() {
                                return tsUrl;
                            }
                        });
                    } catch (Throwable e) {
                        log.warn("Listen on start: " + e.getMessage(), e);
                    }
                }
                //xy为未解密的ts片段，如果存在，则删除
                File tsFile = new File(dir, index + DOWNLOADED_FILE_SUFFIX);
                if (tsFile.exists()) {
                    if (!tsFile.delete()) {
                        if (log.isErrorEnabled()) log.error("删除分片文件失败：{}", tsFile);
                        return M3u8DownloadFactory.DownloadTsType.DEL_FILE_FAIL;
                    }
                }
                byte[] bytes;
                try {
                    bytes = BLOCKING_QUEUE.take();
                } catch (InterruptedException e) {
                    bytes = new byte[Constant.BYTE_COUNT];
                }
                int count = 1;
                HttpURLConnection httpURLConnection = null;
                //重试次数判断
                while (!Thread.interrupted() && count <= retryCount) {
                    InputStream inputStream;
                    try {
                        //模拟http请求获取ts片段文件
                        URL url = new URL(tsUrl);
                        if (proxy == null) {
                            httpURLConnection = (HttpURLConnection) url.openConnection();
                        } else {
                            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                        }
                        httpURLConnection.setConnectTimeout((int) timeoutMillisecond);
                        for (Map.Entry<String, Object> entry : requestHeaderMap.entrySet()) {
                            httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue().toString());
                        }
                        httpURLConnection.setUseCaches(false);
                        httpURLConnection.setReadTimeout((int) timeoutMillisecond);
                        httpURLConnection.setDoInput(true);
                        inputStream = httpURLConnection.getInputStream();

                        if (zeroCopy) {
                            if (M3u8DownloadFactory.DownloadTsType.CONTINUE == downloadPieceFileByZeroCopy(tsFile, bytes, inputStream)) {
                                continue;
                            }
                        } else {
                            if (M3u8DownloadFactory.DownloadTsType.CONTINUE == downloadPieceFileByStream(tsFile, bytes, inputStream)) {
                                continue;
                            }
                        }

                        for (DownloadTaskListener taskListener : taskListenerSet) {
                            try {
                                if (taskListener == null) continue;
                                taskListener.onStart(new DownloadTaskListener.DownloadStartEvent() {
                                    @Override
                                    public String getDownloadUrl() {
                                        return tsUrl;
                                    }

                                    @Override
                                    public DownloadTaskListener.EventType getType() {
                                        return DownloadTaskListener.EventType.START;
                                    }
                                });
                            } catch (Throwable e) {
                                log.warn("Listen on start: " + e.getMessage(), e);
                            }
                        }
                        if (M3u8DownloadFactory.DownloadTsType.DECRYPTED == decryptPieceFileByStream(index, tsFile, bytes)) {
                            if (!tsFile.delete()) {
                                if (log.isWarnEnabled()) log.warn("Failed to delete ts-file: {}", tsFile);
                            }
                        }
                        break;
                    } catch (InterruptedException e) {
                        if (log.isErrorEnabled()) log.error("取消下载分片文件：{}", tsFile);
                        return M3u8DownloadFactory.DownloadTsType.INTERRUPTED;
                    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                        if (log.isErrorEnabled()) log.error("视频文件解密失败：{}", tsFile);
                        return M3u8DownloadFactory.DownloadTsType.DECRYPT_FAIL;
                    } catch (Exception e) {
                        log.debug("第{}获取链接重试！\t{}", count, tsUrl);
                        count++;
                    } finally {
                        try {
                            BLOCKING_QUEUE.put(bytes);
                        } catch (InterruptedException e) {
                            // ignore
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    }
                }
                if (count > retryCount) {
                    if (log.isErrorEnabled()) log.error("下载分片文件超时：{}", tsFile);
                    return M3u8DownloadFactory.DownloadTsType.RETRY_MAX_COUNT;
                }
                finishedCount++;
//               if (log.isInfoEnabled()) log.info(urls + "下载完毕！\t已完成" + finishedCount + "个，还剩" + (tsSet.size() - finishedCount) + "个！");
                if (log.isInfoEnabled()) log.info("下载分片文件成功：{}", tsFile);
                for (DownloadTaskListener taskListener : taskListenerSet) {
                    try {
                        if (taskListener == null) continue;
                        taskListener.onEnd(new DownloadTaskListener.AbstractDownloadEndEvent() {
                            @Override
                            public String getDownloadUrl() {
                                return tsUrl;
                            }
                        });
                    } catch (Throwable e) {
                        log.warn("Listen on end: " + e.getMessage(), e);
                    }
                }
                return M3u8DownloadFactory.DownloadTsType.SUCCESS;
            };
        }

        /**
         * 通过流下载分片文件
         *
         * @param tsFile      分片文件
         * @param bytes       字节缓存
         * @param inputStream 输入流
         * @return M3u8DownloadFactory.DownloadTsType
         * @throws IOException 异常
         */
        private M3u8DownloadFactory.DownloadTsType downloadPieceFileByZeroCopy(File tsFile, byte[] bytes, InputStream inputStream) throws IOException, InterruptedException {
            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    taskListener.beginDownload(new DownloadTaskListener.AbstractBeginDownloadEvent() {
                        @Override
                        public File getSaveFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on begin-download: " + e.getMessage(), e);
                }
            }
            FileOutputStream outputStream = null;
            // 从网络下载视频片段
            try {
                ReadableByteChannel inChannel = Channels.newChannel(inputStream);
                FileChannel outChannel;
                try {
                    outputStream = new FileOutputStream(tsFile);
                    outChannel = outputStream.getChannel();
                } catch (FileNotFoundException e) {
                    if (log.isWarnEnabled()) log.warn(e.getMessage(), e);
                    return M3u8DownloadFactory.DownloadTsType.CONTINUE;
                }
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                int len;
                while ((len = inChannel.read(byteBuffer)) != -1) {
                    byteBuffer.flip();
                    outChannel.write(byteBuffer);
                    byteBuffer.clear();
                    synchronized (this) {
                        // downloadBytes = downloadBytes.add(new BigDecimal(len));
                        downloadBytes += len;
                    }
                }
            } finally {
                IOUtils.closeQuietly(outputStream);
            }

            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    taskListener.doneDownload(new DownloadTaskListener.AbstractDoneDownloadEvent() {
                        @Override
                        public File getSaveFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on done-download: " + e.getMessage(), e);
                }
            }
            return M3u8DownloadFactory.DownloadTsType.VOID;
        }


        /**
         * 通过流下载分片文件
         *
         * @param tsFile      分片文件
         * @param bytes       字节缓存
         * @param inputStream 输入流
         * @return M3u8DownloadFactory.DownloadTsType
         * @throws IOException 异常
         */
        private M3u8DownloadFactory.DownloadTsType downloadPieceFileByStream(File tsFile, byte[] bytes, InputStream inputStream) throws IOException, InterruptedException {
            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    taskListener.beginDownload(new DownloadTaskListener.AbstractBeginDownloadEvent() {
                        @Override
                        public File getSaveFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on begin-download: " + e.getMessage(), e);
                }
            }
            FileOutputStream outputStream = null;
            // 从网络下载视频片段
            try {
                try {
                    outputStream = new FileOutputStream(tsFile);
                } catch (FileNotFoundException e) {
                    if (log.isWarnEnabled()) log.warn(e.getMessage(), e);
                    return M3u8DownloadFactory.DownloadTsType.CONTINUE;
                }
                int len;
                //将未解密的ts片段写入文件
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                    synchronized (this) {
                        // downloadBytes = downloadBytes.add(new BigDecimal(len));
                        downloadBytes += len;
                    }
                }
                outputStream.flush();
            } finally {
                IOUtils.closeQuietly(outputStream);
            }
            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    taskListener.doneDownload(new DownloadTaskListener.AbstractDoneDownloadEvent() {
                        @Override
                        public File getSaveFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on done-download: " + e.getMessage(), e);
                }
            }
            return M3u8DownloadFactory.DownloadTsType.VOID;
        }

        /**
         * 通过流解密分片文件
         *
         * @param tsIndex 序号
         * @param tsFile  分片文件
         * @param bytes   字节缓存
         * @return M3u8DownloadFactory.DownloadTsType
         * @throws IOException                        异常
         * @throws InvalidAlgorithmParameterException 异常
         * @throws NoSuchPaddingException             异常
         * @throws IllegalBlockSizeException          异常
         * @throws NoSuchAlgorithmException           异常
         * @throws BadPaddingException                异常
         * @throws InvalidKeyException                异常
         */
        private M3u8DownloadFactory.DownloadTsType decryptPieceFileByStream(int tsIndex, File tsFile, byte[] bytes) throws IOException, InterruptedException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    taskListener.beginDecrypt(new DownloadTaskListener.AbstractBeginDecryptEvent() {
                        @Override
                        public File getCipherFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on begin-download: " + e.getMessage(), e);
                }
            }
            File decryptFile = null;
            FileInputStream inputStream = null;
            FileOutputStream outputStream = null;
            boolean decrypt = false;
            try {
                inputStream = new FileInputStream(tsFile);
                int available = inputStream.available();
                if (bytes.length < available) bytes = new byte[available];
                int len = inputStream.read(bytes);
                decryptFile = new File(dir, tsIndex + DECODE_FILE_SUFFIX);
                outputStream = new FileOutputStream(decryptFile);
                //开始解密ts片段，这里我们把ts后缀改为了xyz，改不改都一样
                byte[] decryptBytes = decrypt(bytes, available, key, iv, method);
                if (decryptBytes == null) {
                    outputStream.write(bytes, 0, available);
                } else {
                    outputStream.write(decryptBytes);
                }
                finishedFiles.add(decryptFile);
                decrypt = true;
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            for (DownloadTaskListener taskListener : taskListenerSet) {
                try {
                    if (taskListener == null) continue;
                    File finalDecryptFile = decryptFile;
                    taskListener.doneDecrypt(new DownloadTaskListener.AbstractDoneDecryptEvent() {
                        @Override
                        public File getDecryptFile() {
                            return finalDecryptFile;
                        }

                        @Override
                        public File getCipherFile() {
                            return tsFile;
                        }
                    });
                } catch (Throwable e) {
                    log.warn("Listen on done-download: " + e.getMessage(), e);
                }
            }
            return decrypt ? M3u8DownloadFactory.DownloadTsType.DECRYPTED : M3u8DownloadFactory.DownloadTsType.VOID;
        }

        /**
         * 获取所有的ts片段下载链接
         *
         * @return 链接是否被加密，null为非加密
         */
        private String getTsUrl() {
            StringBuilder content = getUrlContent(downloadUrl, false);
            String contentStr = content.toString();
            //判断是否是m3u8链接
            if (!contentStr.contains("#EXTM3U")) throw new M3u8Exception(downloadUrl + "不是m3u8链接！");
            String[] split = contentStr.split("\\n");
            String keyUrl = "";
            boolean isKey = false;
            for (String line : split) {
                //如果含有此字段，则说明只有一层m3u8链接
                if (line.contains("#EXT-X-KEY") || line.contains("#EXTINF")) {
                    isKey = true;
                    keyUrl = downloadUrl;
                    break;
                }
                //如果含有此字段，则说明ts片段链接需要从第二个m3u8链接获取
                if (line.contains(".m3u8")) {
                    if (StringUtils.isUrl(line)) return line;
                    String relativeUrl = downloadUrl.substring(0, downloadUrl.lastIndexOf("/") + 1);
                    if (line.startsWith("/")) line = line.replaceFirst("/", "");
                    keyUrl = mergeUrl(relativeUrl, line);
                    break;
                }
            }
            if (StringUtils.isEmpty(keyUrl)) throw new M3u8Exception("未发现key链接！");
            //获取密钥
            String key1 = isKey ? getKey(keyUrl, content) : getKey(keyUrl, null);
            if (StringUtils.isNotEmpty(key1)) {
                key = key1;
            } else {
                key = null;
            }
            return key;
        }

        /**
         * 获取ts解密的密钥，并把ts片段加入set集合
         *
         * @param url     密钥链接，如果无密钥的m3u8，则此字段可为空
         * @param content 内容，如果有密钥，则此字段可以为空
         * @return ts是否需要解密，null为不解密
         */
        private String getKey(String url, StringBuilder content) {
            StringBuilder urlContent;
            if (content == null || StringUtils.isEmpty(content.toString())) {
                urlContent = getUrlContent(url, false);
            } else {
                urlContent = content;
            }
            if (!urlContent.toString().contains("#EXTM3U")) throw new M3u8Exception(downloadUrl + "不是m3u8链接！");
            String[] split = urlContent.toString().split("\\n");
            for (String s : split) {
                //如果含有此字段，则获取加密算法以及获取密钥的链接
                if (s.contains("EXT-X-KEY")) {
                    String[] split1 = s.split(",");
                    for (String s1 : split1) {
                        if (s1.contains("METHOD")) {
                            method = s1.split("=", 2)[1];
                            continue;
                        }
                        if (s1.contains("URI")) {
                            key = s1.split("=", 2)[1];
                            continue;
                        }
                        if (s1.contains("IV")) iv = s1.split("=", 2)[1];
                    }
                }
            }
            String relativeUrl = url.substring(0, url.lastIndexOf("/") + 1);
            //将ts片段链接加入set集合
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.contains("#EXTINF")) {
                    String s1 = split[++i];
                    String en = null;
                    try {
                        en = URLEncoder.encode(s1, com.sun.xml.internal.ws.commons.xmlutil.Converter.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                        en = s1;
                    }
                    tsSet.add(StringUtils.isUrl(s1) ? s1 : mergeUrl(relativeUrl, en));
                }
            }
            if (!StringUtils.isEmpty(key)) {
                key = key.replace("\"", "");
                return getUrlContent(StringUtils.isUrl(key) ? key : mergeUrl(relativeUrl, key), true).toString().replaceAll("\\s+", "");
            }
            return null;
        }

        /**
         * 模拟http请求获取内容
         *
         * @param urlStr http链接
         * @param isKey  这个url链接是否用于获取key
         * @return 内容
         */
        private StringBuilder getUrlContent(String urlStr, boolean isKey) {
            int count = 1;
            HttpURLConnection httpURLConnection = null;
            StringBuilder content = new StringBuilder();
            while (count <= retryCount) {
                try {
//                    String enUrl = URLEncoder.encode(urlStr, UTF_8);
                    byte[] urlBytes = urlStr.getBytes(UTF_8);
                    String enUrl = new String(urlBytes, StandardCharsets.ISO_8859_1);
                    URL url = new URL(urlStr);
                    if (proxy == null) {
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                    } else {
                        httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                    }
                    httpURLConnection.setConnectTimeout((int) timeoutMillisecond);
                    httpURLConnection.setReadTimeout((int) timeoutMillisecond);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setDoInput(true);
                    for (Map.Entry<String, Object> entry : requestHeaderMap.entrySet())
                        httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue().toString());
                    String line;
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    if (isKey) {
                        byte[] bytes = new byte[128];
                        int len;
                        len = inputStream.read(bytes);
                        isByte = true;
                        if (len == 1 << 4) {
                            keyBytes = Arrays.copyOf(bytes, 16);
                            content.append("isByte");
                        } else {
                            content.append(new String(Arrays.copyOf(bytes, len)));
                        }
                        return content;
                    }
                    while ((line = bufferedReader.readLine()) != null) content.append(line).append("\n");
                    bufferedReader.close();
                    inputStream.close();
                    if (log.isInfoEnabled()) log.info(content.toString());
                    break;
                } catch (Exception e) {
                    log.debug("第{}获取链接重试！\t{}", count, urlStr);
                    count++;
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
            if (count > retryCount) throw new M3u8Exception("连接超时！");
            return content;
        }

        /**
         * 解密ts
         *
         * @param sSrc   ts文件字节数组
         * @param length 长度
         * @param sKey   密钥
         * @return 解密后的字节数组
         */
        private byte[] decrypt(byte[] sSrc, int length, String sKey, String iv, String method) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            if (StringUtils.isNotEmpty(method) && !method.contains("AES")) throw new M3u8Exception("未知的算法！");
            // 判断Key是否正确
            if (StringUtils.isEmpty(sKey)) return null;
            // 判断Key是否为16位
            if (sKey.length() != 16 && !isByte) {
                throw new M3u8Exception("Key长度不是16位！");
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec keySpec = new SecretKeySpec(isByte ? keyBytes : sKey.getBytes(UTF_8), "AES");
            byte[] ivByte;
            if (iv.startsWith("0x")) ivByte = StringUtils.hexStringToByteArray(iv.substring(2));
            else ivByte = iv.getBytes();
            if (ivByte.length != 16) ivByte = new byte[16];
            //如果m3u8有IV标签，那么IvParameterSpec构造函数就把IV标签后的内容转成字节数组传进去
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return cipher.doFinal(sSrc, 0, length);
        }

        /**
         * 字段校验
         */
        private void checkField() {
            if ("m3u8".compareTo(MediaFormat.getMediaFormat(downloadUrl)) != 0) throw new M3u8Exception(downloadUrl + "不是一个完整m3u8链接！");
            if (threadCount <= 0) throw new M3u8Exception("同时下载线程数只能大于0！");
            if (retryCount < 0) throw new M3u8Exception("重试次数不能小于0！");
            if (timeoutMillisecond < 0) throw new M3u8Exception("超时时间不能小于0！");
            if (StringUtils.isEmpty(dir)) throw new M3u8Exception("视频存储目录不能为空！");
            if (StringUtils.isEmpty(filename)) throw new M3u8Exception("视频名称不能为空！");
            reset();
        }

        // 重置
        private void reset() {
            finishedCount = 0;
            method = "";
            key = "";
            isByte = false;
            iv = "";
            tsSet.clear();
            finishedFiles.clear();
            // downloadBytes = new BigDecimal(0);
            downloadBytes = 0;
        }

        private String mergeUrl(String start, String end) {
            if (end.startsWith("/")) {
                end = end.replaceFirst("/", "");
            }
            int position = 0;
            String subEnd, tempEnd = end;
            while ((position = end.indexOf("/", position)) != -1) {
                subEnd = end.substring(0, position + 1);
                if (start.endsWith(subEnd)) {
                    tempEnd = end.replaceFirst(subEnd, "");
                    break;
                }
                ++position;
            }
            return start + tempEnd;
        }

    }
}
