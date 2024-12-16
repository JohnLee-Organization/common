/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-12
 * @time : 20:20
 */
package net.lizhaoweb.hls;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 媒体类型
 * <p>
 * Created by Jhon on 2024/12/12 20:20
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MediaFormat {
    mp4("mp4"), //
    mkv("mkv"), //
    webm("webm"), //
    gif("gif"), //
    mov("mov"), //
    ogg("ogg"), //
    flv("flv"), //
    avi("avi"), //
    m3gp("3gp"), //
    wmv("wmv"), //
    mpg("mpg"), //
    vob("vob"), //
    swf("swf"), //
    m3u8("m3u8"), //
    ;

    @NonNull
    private String name;

    public static MediaFormat find(String name) {
        for (MediaFormat format : values()) {
            if (format.name.equals(name)) return format;
        }
        throw new M3u8Exception("非视频格式！");
    }

    public static MediaFormat find4Url(String url) {
        if (!StringUtils.isUrl(url)) {
            throw new M3u8Exception(url + "不是一个完整URL链接！");
        }
//        url = url.substring(url.lastIndexOf("/") + 1);
        url = url.substring(url.lastIndexOf(".") + 1);
        return find(url);
    }

}
