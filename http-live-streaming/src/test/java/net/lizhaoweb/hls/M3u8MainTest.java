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

        String filename = "test";
        String M3U8URL = "https://youku.cdn-56.com/20180109/2SwCGxb4/index.m3u8";

//        Map<String, Object> headersMap = new HashMap<>();
//        headersMap.put("Content-Type", "text/html;charset=utf-8");

        long[] startTime = {0, 0};

        M3u8DownloadFactory.getInstance(M3U8URL) // 获取下载实例
                .savePath("F://m3u8JavaTest") //设置生成目录
                .filename(filename) //设置视频名称
                .threadSize(10) //设置线程数
                .retry(30) //设置重试次数
                .timeout(10L, TimeUnit.SECONDS) //设置连接超时时间
                .listenInterval(5L, TimeUnit.SECONDS) //设置监听器间隔
                .withZeroCopy() // 启用零拷贝技术

//                //添加额外请求头
//                .addRequestHeader("Content-Type", "text/html;charset=utf-8")
//                .addRequestHeaderMap(headersMap)

//                //如果需要的话设置http代理
//                .withProxy(8090)
//                .withProxy("172.50.60.3", 8090)
//                .withProxy(Proxy.Type.HTTP, "172.50.60.3", 8090)

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

                .start();//开始下载
    }
}
