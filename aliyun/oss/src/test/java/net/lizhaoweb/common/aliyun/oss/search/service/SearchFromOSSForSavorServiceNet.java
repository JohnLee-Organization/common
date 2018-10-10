/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.aliyun.oss.search.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:17
 */
package net.lizhaoweb.common.aliyun.oss.search.service;

import com.aliyun.oss.model.OSSObjectSummary;
import net.lizhaoweb.common.aliyun.oss.IOSSClient;
import net.lizhaoweb.common.aliyun.oss.model.OSSObjectSummaryList;
import net.lizhaoweb.common.aliyun.oss.search.config.BoxConfigNet;
import net.lizhaoweb.common.aliyun.oss.search.config.GlobalConfig;
import net.lizhaoweb.common.aliyun.oss.search.model.BoxInfoBean;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.date.DateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class SearchFromOSSForSavorServiceNet implements ISearchFromOSSForSavorService {

    private static final String OSS_OBJECT_KEY_PREFIX = "log/box/";

    private IOSSClient ossClient;

    public SearchFromOSSForSavorServiceNet(IOSSClient ossClient) {
        super();
        this.ossClient = ossClient;
    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Override
    public void boxLogExistFromOSSSimpleAll() {

        Pattern getBoxMacPattern = Pattern.compile(BoxConfigNet.BOX_REGEX_ALL);
        List<String> boxMacList = Arrays.asList(BoxConfigNet.BOX_MAC_LOG_ARRAY);
        String startMarker = null;
        boolean isHasMore = false;
        do {
            OSSObjectSummaryList ossObjectSummaryList = ossClient.listObjects(GlobalConfig.BUCKET_NAME, OSS_OBJECT_KEY_PREFIX, startMarker, BoxConfigNet.KEYS_SIZE);
            List<OSSObjectSummary> ossObjectSummaryList_ = ossObjectSummaryList.getList();

            for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList_) {
                String ossObjectKey = ossObjectSummary.getKey();
                if (StringUtils.isBlank(ossObjectKey)) {
                    continue;
                }
                Matcher getBoxMacMatcher = getBoxMacPattern.matcher(ossObjectKey);
                if (!getBoxMacMatcher.find()) {
                    continue;
                }
                String boxMacLog = getBoxMacMatcher.group(1);
                if (!boxMacList.contains(boxMacLog)) {
                    continue;
                }

                // 打印
                System.out.println(ansi().eraseScreen().render(this.getOssKeyPrintString(ossObjectKey)));
            }

            startMarker = ossObjectSummaryList.getNextMarker();
            isHasMore = ossObjectSummaryList.isHasMore();
        } while (isHasMore);

    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Override
    public void boxLogExistFromOSSSimple() {
        System.out.println("网络版");
        Pattern boxMacPattern = Pattern.compile(BoxConfigNet.BOX_REGEX_SIMPLE);
        List<String> boxMacList = Arrays.asList(BoxConfigNet.BOX_MAC_ARRAY);
        Calendar startCalendarForDir = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_START, "yyyyMMdd");
        Calendar endCalendarForDir = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_END, "yyyyMMdd");
        Calendar startCalendarForFile = DateUtil.stringToCalendar(BoxConfigNet.FILE_DATE_START, "yyyyMMddHH");
        Calendar endCalendarForFile = DateUtil.stringToCalendar(BoxConfigNet.FILE_DATE_END, "yyyyMMddHH");
        Calendar indexCalendar = null;
        for (indexCalendar = (Calendar) startCalendarForDir.clone(); indexCalendar.before(endCalendarForDir); indexCalendar.set(Calendar.DATE, indexCalendar.get(Calendar.DATE) + 1)) {
//            System.out.println(DateUtil.calendarToString(indexCalendar,"yyyy-MM-dd HH;mm:ss"));
            String prefix = String.format("log/box/1/%s/", DateUtil.calendarToString(indexCalendar, "yyyyMMdd"));
            String startMarker = null;
            boolean isHasMore = false;
            do {
                OSSObjectSummaryList ossObjectSummaryList = ossClient.listObjects(GlobalConfig.BUCKET_NAME, prefix, startMarker, BoxConfigNet.KEYS_SIZE);
                List<OSSObjectSummary> ossObjectSummaryList_ = ossObjectSummaryList.getList();

                for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList_) {
                    String ossObjectKey = ossObjectSummary.getKey();
                    if (StringUtils.isBlank(ossObjectKey)) {
                        continue;
                    }
                    Matcher boxMacMatcher = boxMacPattern.matcher(ossObjectKey);
                    if (!boxMacMatcher.find()) {
                        continue;
                    }
                    String boxMac = boxMacMatcher.group(1);
                    if (!boxMacList.contains(boxMac)) {
                        continue;
                    }

                    Calendar logFileCalendar = DateUtil.stringToCalendar(boxMacMatcher.group(2), "yyyyMMddHH");
                    if (logFileCalendar.before(startCalendarForFile) || logFileCalendar.after(endCalendarForFile)) {
                        continue;
                    }
                    // 打印
                    System.out.println(ansi().eraseScreen().render(this.getOssKeyPrintString(ossObjectKey)));
                }

                startMarker = ossObjectSummaryList.getNextMarker();
                isHasMore = ossObjectSummaryList.isHasMore();
            } while (isHasMore);
        }
    }

    @Override
    public void boxLog0BytesInOSS() {
        System.out.println("/===================================================================\\");
        Pattern getBoxMacPattern = Pattern.compile(BoxConfigNet.BOX_REGEX_SIMPLE);
        Calendar startCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_START, "yyyyMMdd");
        Calendar endCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_END, "yyyyMMdd");
        Map<String, Integer> map = new ConcurrentHashMap<>();
        Calendar indexCalendar = null;
        int boxLog0BytesFiesTotalCount = 0, boxLog0BytesFiesMacCount = 0;
        for (indexCalendar = (Calendar) startCalendar.clone(); indexCalendar.before(endCalendar); indexCalendar.set(Calendar.DATE, indexCalendar.get(Calendar.DATE) + 1)) {
            String prefix = String.format("log/box/1/%s/", DateUtil.calendarToString(indexCalendar, "yyyyMMdd"));
            String startMarker = null;
            boolean isHasMore = false;
            do {
                OSSObjectSummaryList ossObjectSummaryList = ossClient.listObjects(GlobalConfig.BUCKET_NAME, prefix, startMarker, BoxConfigNet.KEYS_SIZE);
                List<OSSObjectSummary> ossObjectSummaryList_ = ossObjectSummaryList.getList();

                for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList_) {
                    String ossObjectKey = ossObjectSummary.getKey();
                    if (StringUtils.isBlank(ossObjectKey)) {
                        continue;
                    }
                    Matcher getBoxMacMatcher = getBoxMacPattern.matcher(ossObjectKey);
                    if (!getBoxMacMatcher.find()) {
                        continue;
                    }
                    long ossObjectSize = ossObjectSummary.getSize();
                    if (ossObjectSize > 0) {
                        continue;
                    }
                    boxLog0BytesFiesTotalCount++;

                    String boxMac = getBoxMacMatcher.group(1);
                    Integer boxLog0BytesCount = map.get(boxMac);
                    if (boxLog0BytesCount == null || boxLog0BytesCount < 1) {
                        boxLog0BytesCount = 1;
                    } else {
                        boxLog0BytesCount++;
                    }
                    map.put(boxMac, boxLog0BytesCount);


                    // 打印
                    System.out.println(ansi().eraseScreen().render(this.getOssKeyPrintString(ossObjectKey)));
                }

                startMarker = ossObjectSummaryList.getNextMarker();
                isHasMore = ossObjectSummaryList.isHasMore();
            } while (isHasMore);
        }
        System.out.println("---------------------------------------------------------------------");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String boxMac = entry.getKey();
            Integer boxLog0BytesCount = entry.getValue();
            boxLog0BytesFiesMacCount++;
            System.out.println(ansi().eraseScreen().render(String.format("@|blue [Box-Mac]|@@|yellow %s|@	@|blue [OSS-Key]|@@|cyan %s|@", boxMac, boxLog0BytesCount)));
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println(ansi().eraseScreen().render(String.format("@|blue [0-Bytes-Files-Total-Count]|@@|yellow %s|@	@|blue [0-Bytes-Files-Mac-Count]|@@|cyan %s|@", boxLog0BytesFiesTotalCount, boxLog0BytesFiesMacCount)));

        System.out.println("\\===================================================================/");
    }

    @Override
    public void analysisMac() {
        System.out.println("/===================================================================\\");
        Pattern getBoxMacPattern = Pattern.compile(BoxConfigNet.BOX_REGEX_SIMPLE);
        Calendar startCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_START, "yyyyMMdd");
        Calendar endCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_END, "yyyyMMdd");
        Map<String, Integer> map = new ConcurrentHashMap<>();
        Calendar indexCalendar = null;
        int boxLog0BytesFiesTotalCount = 0, boxLog0BytesFiesMacCount = 0;
        for (indexCalendar = (Calendar) startCalendar.clone(); indexCalendar.before(endCalendar); indexCalendar.set(Calendar.DATE, indexCalendar.get(Calendar.DATE) + 1)) {
            String prefix = String.format("log/box/1/%s/", DateUtil.calendarToString(indexCalendar, "yyyyMMdd"));
            String startMarker = null;
            boolean isHasMore = false;
            do {
                OSSObjectSummaryList ossObjectSummaryList = ossClient.listObjects(GlobalConfig.BUCKET_NAME, prefix, startMarker, BoxConfigNet.KEYS_SIZE);
                List<OSSObjectSummary> ossObjectSummaryList_ = ossObjectSummaryList.getList();

                for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList_) {
                    String ossObjectKey = ossObjectSummary.getKey();
                    if (StringUtils.isBlank(ossObjectKey)) {
                        continue;
                    }
                    Matcher getBoxMacMatcher = getBoxMacPattern.matcher(ossObjectKey);
                    if (!getBoxMacMatcher.find()) {
                        continue;
                    }
//                    long ossObjectSize = ossObjectSummary.getSize();
//                    if (ossObjectSize > 0) {
//                        continue;
//                    }
                    boxLog0BytesFiesTotalCount++;

                    String boxMac = getBoxMacMatcher.group(1);
                    Integer boxLog0BytesCount = map.get(boxMac);
                    if (boxLog0BytesCount == null || boxLog0BytesCount < 1) {
                        boxLog0BytesCount = 1;
                    } else {
                        boxLog0BytesCount++;
                    }
                    map.put(boxMac, boxLog0BytesCount);


                    // 打印
                    System.out.println(ansi().eraseScreen().render(this.getOssKeyPrintString(ossObjectKey)));
                }

                startMarker = ossObjectSummaryList.getNextMarker();
                isHasMore = ossObjectSummaryList.isHasMore();
            } while (isHasMore);
        }
        System.out.println("---------------------------------------------------------------------");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String boxMac = entry.getKey();
            Integer boxLog0BytesCount = entry.getValue();
            boxLog0BytesFiesMacCount++;
            System.out.println(ansi().eraseScreen().render(String.format("@|blue [Box-Mac]|@@|yellow %s|@	@|blue [OSS-Key]|@@|cyan %s|@", boxMac, boxLog0BytesCount)));
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println(ansi().eraseScreen().render(String.format("@|blue [0-Bytes-Files-Total-Count]|@@|yellow %s|@	@|blue [0-Bytes-Files-Mac-Count]|@@|cyan %s|@", boxLog0BytesFiesTotalCount, boxLog0BytesFiesMacCount)));

        System.out.println("\\===================================================================/");
    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Override
    public void boxLogExistFromOSSDetail() {
        try {
            byte[] contentBytes = BoxConfigNet.BOX_INFO.getBytes(Constant.Charset.UTF8);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
            List<String> lineList = IOUtils.readLines(byteArrayInputStream, Constant.Charset.UTF8);

            Pattern hotelStartPattern = Pattern.compile("^\\s*(\\S+)\\s*\\|\\[\\s*$");
            Pattern boxInfoPattern = Pattern.compile("(\\d+)\\s+(\\S+)\\s+(\\S+)\\s+\\S+:(\\d+)\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)\\s+(\\S+)\\s+(\\S+)\\s*");
            Pattern hotelEndPattern = Pattern.compile("^\\s*\\]\\|\\s*$");

            String hotelName = null;// 酒楼名称
            Map<String, BoxInfoBean> boxInfoBeanMap = new HashMap<>();
            for (String line : lineList) {
                Matcher hotelStartMatcher = hotelStartPattern.matcher(line);
                if (hotelStartMatcher.find()) {
                    hotelName = hotelStartMatcher.group(1);
                    continue;
                }
                Matcher boxInfoMatcher = boxInfoPattern.matcher(line);
                if (boxInfoMatcher.find()) {
                    String id = boxInfoMatcher.group(1);// 机顶盒ID
                    String roomName = boxInfoMatcher.group(2);// 包间名称
                    String name = boxInfoMatcher.group(3);// 机顶盒名称
                    String tvCount = boxInfoMatcher.group(4);// 电视数量
                    String mac = boxInfoMatcher.group(5);// mac地址
                    String switchingTime = boxInfoMatcher.group(6);// 切换时间
                    String volume = boxInfoMatcher.group(7);// 音量
                    String frozenState = boxInfoMatcher.group(8);// 冻结状态
                    String deletedState = boxInfoMatcher.group(9);// 删除状态

                    BoxInfoBean boxInfoBean = new BoxInfoBean(id, name, mac, hotelName, roomName, tvCount, switchingTime, volume, frozenState, deletedState);
                    boxInfoBeanMap.put(boxInfoBean.getMac(), boxInfoBean);
                    continue;
                }
                Matcher hotelEndMatcher = hotelEndPattern.matcher(line);
                if (hotelEndMatcher.find()) {
                    hotelName = null;
                }
            }

            Pattern getBoxMacPattern = Pattern.compile(BoxConfigNet.BOX_REGEX_DETAIL);
            Calendar startCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_START, "yyyyMMdd");
            Calendar endCalendar = DateUtil.stringToCalendar(BoxConfigNet.DIRECTORY_DATE_END, "yyyyMMdd");
            Calendar indexCalendar = null;
            for (indexCalendar = (Calendar) startCalendar.clone(); indexCalendar.before(endCalendar); indexCalendar.set(Calendar.DATE, indexCalendar.get(Calendar.DATE) + 1)) {
                String prefix = String.format("log/box/1/%s/", DateUtil.calendarToString(indexCalendar, "yyyyMMdd"));
                String startMarker = null;
                boolean isHasMore = false;
                do {
                    OSSObjectSummaryList ossObjectSummaryList = ossClient.listObjects(GlobalConfig.BUCKET_NAME, prefix, startMarker, BoxConfigNet.KEYS_SIZE);
                    List<OSSObjectSummary> ossObjectSummaryList_ = ossObjectSummaryList.getList();

                    for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList_) {
                        String ossObjectKey = ossObjectSummary.getKey();
                        if (StringUtils.isBlank(ossObjectKey)) {
                            continue;
                        }
                        Matcher getBoxMacMatcher = getBoxMacPattern.matcher(ossObjectKey);
                        if (!getBoxMacMatcher.find()) {
                            continue;
                        }
                        String boxMac = getBoxMacMatcher.group(1);
                        BoxInfoBean boxInfoBean = boxInfoBeanMap.get(boxMac);
                        if (boxInfoBean == null) {
                            continue;
                        }

                        // 打印
                        String systemOutMessage = String.format(
//                                "@|blue [酒楼]|@%s\t@|blue [包间]|@%s\t@|blue [机顶盒]|@%s\t@|blue [机顶盒MAC]|@%s\t@|blue [冻结]|@%s\t@|blue [删除]|@%s\t%s",
                                "@|blue [酒楼]|@%s\t@|blue [包间]|@%s\t@|blue [机顶盒]|@%s\t@|blue [冻结]|@%s\t@|blue [删除]|@%s\t%s",
                                boxInfoBean.getHotelName(),
                                boxInfoBean.getRoomName(),
                                boxInfoBean.getName(),
//                                boxInfoBean.getMac(),
                                boxInfoBean.getFrozenState(),
                                boxInfoBean.getDeletedState(),
                                this.getOssKeyPrintString(ossObjectKey)
                        );
                        System.out.println(ansi().eraseScreen().render(systemOutMessage));
                    }

                    startMarker = ossObjectSummaryList.getNextMarker();
                    isHasMore = ossObjectSummaryList.isHasMore();
                } while (isHasMore);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOssKeyPrintString(String ossObjectKey) {
        String printMessage = ossObjectKey;
        Pattern pattern = Pattern.compile("^log/box/\\d+/(\\d+)/([a-zA-Z0-9]{12})_(\\d+)\\.blog\\.zip$");
        Matcher matcher = pattern.matcher(ossObjectKey);
        if (matcher.find()) {
            printMessage = String.format("@|blue [Up-Date]|@@|green %s|@\t@|blue [Log-Date]|@@|magenta %s|@\t@|blue [Box-Mac]|@@|yellow %s|@\t@|blue [OSS-Key]|@@|cyan %s|@", this.turnDateFormat(matcher.group(1), "yyyyMMdd", "yyyy-MM-dd"), this.turnDateFormat(matcher.group(3), "yyyyMMddHH", "yyyy-MM-dd HH:mm:ss"), matcher.group(2), ossObjectKey);
        }
        return printMessage;
    }

    private String turnDateFormat(String srcDateString, String srcFormat, String tarFormat) {
        Date date = DateUtil.stringToDate(srcDateString, srcFormat);
        return DateUtil.dateToString(date, tarFormat);
    }
}
