[《视频篇》java实现下载hls（m3u8+ts）实时流并进行合并mp4](https://www.cnblogs.com/fusio/p/18007824)

[java spring 实现 下载hls(m3u8+ts)实时流并进行合并mp4和压缩](https://blog.csdn.net/qq_41604890/article/details/130143355)

[java下载m3u8视频，解密并合并ts（一）](https://blog.csdn.net/qq494257084/article/details/103550171)

[java下载m3u8视频，解密并合并ts（二）](https://blog.csdn.net/qq494257084/article/details/103550902)

[java下载m3u8视频，解密并合并ts（三）](https://blog.csdn.net/qq494257084/article/details/103551293)

M3U8编码格式
m3u8基本上可以认为就是.m3u格式文件，区别在于，m3u8文件使用UTF-8字符编码。

```
// m3u文件头，必须放在第一行
#EXTM3U
// 定义当前m3u8文件中第一个文件的序列号，每个ts文件在m3u8文件中都有固定唯一的序列号
// 该序列号用于在MBR时切换码率进行对齐
#EXT-X-MEDIA-SEQUENCE
// 每个分片TS的最大的时长
#EXT-X-TARGETDURATION
// 是否允许cache
#EXT-X-ALLOW-CACHE
// m3u8文件结束符
#EXT-X-ENDLIST
// 分片TS的信息，如时长，带宽等
#EXTINF
// 定义加密方式和key文件的url，用于取得16bytes的key文件解码ts文件
#EXT-X-KEY
// 提供关于PlayList的可变性的信息，对整个PlayList文件有效，是可选项。
// 格式如下：#EXT-X-PLAYLIST-TYPE：VOD（或者EVENT）。VOD表示服务器不能改变PlayList 文件；
// EVENT则表示服务器不能改变或是删除PlayList文件中的任何部分，但是可以向该文件中增加新的一行内容。
#EXT-X-PLAYLIST-TYPE
```

