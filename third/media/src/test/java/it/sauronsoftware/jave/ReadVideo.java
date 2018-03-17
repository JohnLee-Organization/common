package it.sauronsoftware.jave;

import java.io.File;

/**
 * Created by Administrator on 2018/3/17 0017.
 */
public class ReadVideo {
    public static void main(String[] args) {
        File source = new File("D:\\AV\\最新一本道041914_793-成宫ルリ AV女优联络电话 宅访问.avi");
        Encoder encoder = new Encoder();
        try {
            MultimediaInfo m = encoder.getInfo(source);
            long ls = m.getDuration();
            int millisecond = (int) (ls % 1000);
            int second = (int) (ls / 1000 % 60);
            int minute = (int) (ls / 60000 % 60);
            int hour = (int) (ls / 3600000);
            System.out.println(String.format("此视频时长为:%d小时%d分%d秒%d毫秒！", hour, minute, second, millisecond));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
