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


import java.util.HashSet;
import java.util.Set;

/**
 * 媒体类型
 * <p>
 * Created by Jhon on 2024/12/12 20:20
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class MediaFormat {

    private static Set<String> set = new HashSet<>();

    static {
        set.add("mp4");
        set.add("mkv");
        set.add("webm");
        set.add("gif");
        set.add("mov");
        set.add("ogg");
        set.add("flv");
        set.add("avi");
        set.add("3gp");
        set.add("wmv");
        set.add("mpg");
        set.add("vob");
        set.add("swf");
        set.add("m3u8");
    }

    private MediaFormat() {
    }

    public static String getMediaFormat(String url) {
        if (!StringUtils.isUrl(url))
            throw new M3u8Exception(url + "不是一个完整URL链接！");
        url = url.substring(url.lastIndexOf("/") - 1);
        for (String s : set) {
            if (url.contains(s))
                return s;
        }
        throw new M3u8Exception("非视频链接！");
    }
}
