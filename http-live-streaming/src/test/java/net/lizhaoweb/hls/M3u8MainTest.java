/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-13
 * @time : 02:20
 */
package net.lizhaoweb.hls;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * a
 * <p>
 * Created by Jhon on 2024/12/13 2:20
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class M3u8MainTest {

    public static void main(String[] args) {
        String workPath = System.getProperty("user.dir");
        if (args != null && args.length > 0) {
            workPath = args[0];
        }
        File appDir = new File(workPath);
        File confDir = new File(appDir, "conf");
        File doneDir = new File(appDir, "done");
        File destDir = new File(appDir, "dest");
        File tempDir = new File(appDir, "temp");
        File downDir = new File(tempDir, "down");
        File decrDir = new File(tempDir, "decr");

        System.out.println("\n\n\n\n\n==============================================================================================");
        System.out.printf("请在‘%s’目录下创建以‘.cnf’结尾的下载配置文件。\n", confDir.getAbsolutePath());
        System.out.println("文件内容可以有多行数据，每行是一条下载配置。");
        System.out.println("每行内容以一个‘|’分割为两部分。‘|’前面的是内容是m3u8的下载url，‘|’后面的内容是保存的文件名。");
        System.out.printf("下载后的文件保存在‘%s’目录下。\n", destDir.getAbsolutePath());
        System.out.printf("%f秒后开始检索下载……\n", 10F);
        System.out.println("==============================================================================================\n\n\n\n\n");

        if (!confDir.exists()) {
            if (!confDir.mkdirs()) {
                throw new M3u8Exception(String.format("创建‘%s’目录失败", confDir));
            }
        }
        if (!doneDir.exists()) {
            if (!doneDir.mkdirs()) {
                throw new M3u8Exception(String.format("创建‘%s’目录失败", doneDir));
            }
        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ignored) {
        }

        first_loop:
        while (!Thread.interrupted()) {
            try {
                File[] confFiles = confDir.listFiles(f -> f.getName().endsWith(".cnf"));
                if (confFiles == null || confFiles.length < 1) {
                    try {
                        System.out.println("没有要下载的任务，15秒后重新检索任务");
                        TimeUnit.SECONDS.sleep(15); // 没有下载任务，第15秒查看一次
                    } catch (InterruptedException e) {
                        break;
                    }
                    continue;
                }
                for (File confFile : confFiles) {
                    if (confFile == null) continue;
                    FileInputStream fileInputStream = null;
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileInputStream = new FileInputStream(confFile);
                        List<String> lines = IOUtils.readLines(fileInputStream, StandardCharsets.UTF_8);

                        String confFilename = confFile.getName();
                        File doneFile = new File(doneDir, confFilename + ".done");
                        fileOutputStream = new FileOutputStream(doneFile);
                        if (lines.isEmpty()) continue;
                        for (String line : lines) {
                            try {
                                String[] lineSplit = line.split("\\|");
                                if (lineSplit.length < 2) {
                                    continue;
                                }
                                String m3u8Url = lineSplit[0];
                                if (StringUtils.isEmpty(m3u8Url)) {
                                    continue;
                                }
                                String filename = lineSplit[1];
                                if (StringUtils.isEmpty(filename)) {
                                    filename = "file-" + System.currentTimeMillis();
                                }
                                m3u8Url = m3u8Url.trim();
                                filename = filename.trim();

//                                String filename = "";
//                                String urlEncodeFilename = URLEncoder.encode(filename, UTF_8);
//                                String m3u8Url = "https://youku.cdn-56.com/20180109/2SwCGxb4/index.m3u8";
//                                String savePath = "F://m3u8JavaTest";
//
//                                Map<String, Object> headersMap = new HashMap<>();
//                                headersMap.put("Content-Type", "text/html;charset=utf-8");

                                long[] startTime = {0, 0};
                                M3u8DownloadFactory.M3u8Downloader downloader = M3u8DownloadFactory.getInstance(m3u8Url) // 获取下载实例
                                        .destDir(destDir) //设置生成目录
                                        .downloadDir(downDir) // 下载文件存放目录
                                        .decryptDir(decrDir) // 解密文件存放目录
                                        .filename(filename) //设置视频名称
                                        .threadSize(100) //设置线程数
                                        .retry(3000) //设置重试次数
                                        .timeout(10L, TimeUnit.SECONDS) //设置连接超时时间
                                        .listenInterval(5L, TimeUnit.SECONDS) //设置监听器间隔
//                                        .withZeroCopy() // 启用零拷贝技术
//
//                                        //添加额外请求头
//                                        .addRequestHeader("Content-Type", "text/html;charset=utf-8")
//                                        .addRequestHeaderMap(headersMap)
//
//                                        //如果需要的话设置http代理
//                                        .withProxy(8090)
//                                        .withProxy("172.50.60.3", 8090)
//                                        .withProxy(Proxy.Type.HTTP, "172.50.60.3", 8090)

                                        //添加监听器 - 开始
                                        .addListener(new DownloadListener() {
                                            @Override
                                            public void onStart(DownloadStartEvent event) {
                                                startTime[0] = System.currentTimeMillis();
                                                System.out.println("开始下载！");
                                            }

                                            @Override
                                            public void process(DownloadProcessEvent event) {
                                                System.out.println("\n下载网址：" + event.getDownloadUrl() + "\n已下载" + event.getFinished() + "个\t一共" + event.getTotal() + "个\t已完成" + event.getPercent() + "%\n");
                                            }

                                            @Override
                                            public void speed(DownloadSpeedEvent event) {
                                                System.out.printf("%s[%s] 下载速度：%s\n", Thread.currentThread().getName(), Thread.currentThread().getId(), event.speed());
                                            }

                                            @Override
                                            public void onEnd(DownloadEndEvent event) {
                                                System.out.println("下载完毕");
                                                startTime[1] = System.currentTimeMillis();

                                                System.out.printf("耗时 %f 秒", (startTime[1] - startTime[0]) / 1000.0);
                                            }

                                        })//添加监听器 - 结束
                                        ;

                                downloader.start();//开始下载
                                M3u8DownloadFactory.destroy();

                                fileOutputStream.write(line.getBytes());
                                fileOutputStream.flush();

                            } catch (Throwable e) {
                                throw new RuntimeException("下载视频时异常", e);
                            }
                        }
                        TimeUnit.MILLISECONDS.sleep(500);
                        fileOutputStream.flush();
                    } catch (Throwable e) {
                        throw new Exception("读取配置文件时异常", e);
                    } finally {
                        IOUtils.closeQuietly(fileInputStream);
                        IOUtils.closeQuietly(fileOutputStream);
                    }
                    if (!confFile.delete()) {
                        System.out.printf("\n\n\n\n\n删除配置文件为%s失败\n\n\n\n\n\n", confFile);
                        break first_loop;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

        }
    }

}
