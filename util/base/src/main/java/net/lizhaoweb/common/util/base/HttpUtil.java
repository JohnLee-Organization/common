package net.lizhaoweb.common.util.base;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import net.lizhaoweb.common.util.base.Constant.Charset;
import net.lizhaoweb.common.util.exception.UserAgentException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>HTTP工具类</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public final class HttpUtil {

    private HttpUtil() {
        super();
    }

    /**
     * 响浏览器输出字符串。
     *
     * @param response HTTP响应对象。
     * @param string   要转JSON的对象。
     */
    public static void print(HttpServletResponse response, String string) {
        print(response, string, Charset.UTF8);
    }

    /**
     * 响浏览器输出字符串。
     *
     * @param response HTTP响应对象。
     * @param string   要转JSON的对象。
     * @param charset  字符集。
     */
    public static void print(HttpServletResponse response, String string, String charset) {
        try {
            response.setCharacterEncoding(charset);
            response.setContentType(String.format("text/html;charset=%s", charset));
            response.getWriter().print(string);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 响浏览器输出字符串。
     *
     * @param outputStream Servlet输出流。
     * @param string       要转JSON的对象。
     * @param charset      字符集。
     */
    public static void print(ServletOutputStream outputStream, String string, String charset) {
        try {
            outputStream.write(string.getBytes(charset));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 输出JSON字符中。
     *
     * @param response HTTP响应对象。
     * @param object   要转JSON的对象。
     */
    public static void printJson(HttpServletResponse response, Object object) {
        printJson(response, object, Charset.UTF8);
    }

    /**
     * 输出JSON字符中。
     *
     * @param response HTTP响应对象。
     * @param object   要转JSON的对象。
     * @param charset  字符集。
     */
    public static void printJson(HttpServletResponse response, Object object, String charset) {
        String json = JsonUtil.toJson(object);
        print(response, json, charset);
    }

    /**
     * 格式化网站路径。
     *
     * @param webPath 网站路径
     * @return 返回格式化的访问URL。
     */
    public static final String formatPath(String webPath) {
        if (StringUtil.isBlank(webPath)) {
            return webPath;
        }
        String result = webPath;
        while (result.indexOf("\\") > -1) {
            result = result.replace("\\", "/");
        }
        while (result.indexOf("//") > -1) {
            result = result.replace("//", "/");
        }
        result = result.replace(":/", "://");
        return result;
    }

    /**
     * 获得项目的访问路径。
     *
     * @param request 请求对象
     * @return 返回网站的根URL。
     */
    public static final String getWebPath(HttpServletRequest request) {
        String result = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80) {
            result += ":" + request.getServerPort();
        }
        result += request.getContextPath();
        return result;
    }

    /**
     * ip校验
     *
     * @param string IP字符串
     * @return 返回TRUE或FLASE。
     */
    public static Boolean isIpAddress(String string) {
        Pattern pattern = Pattern.compile(Constant.Regex.IP);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * 获取客户端ip
     *
     * @param request 请求对象
     * @return 返回IP字符串。
     */
    public static String getClientAddress(HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        if (address != null && isIpAddress(address)) {
            return address;
        }
        return request.getRemoteAddr();
    }

    /**
     * 文件下载。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param file     要下载的文件
     * @param fileName 文件保存的名称
     * @param charset  字符集编码
     * @throws UserAgentException 异常
     */
    public static final void downFile(HttpServletRequest request, HttpServletResponse response, File file, String fileName, String charset) throws UserAgentException {
        System.out.println("==============User-Agent============" + request.getHeader("User-Agent"));
        boolean access = request.getHeader("User-Agent") != null && (request.getHeader("User-Agent").toLowerCase().indexOf("mozilla") > -1 || request.getHeader("User-Agent").toLowerCase().indexOf("safari") > -1 || request.getHeader("User-Agent").toLowerCase().indexOf("chrome") > -1 || request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1 || request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1 || request.getHeader("User-Agent").toUpperCase().indexOf("DS-USER") > -1 || request.getHeader("User-Agent").toUpperCase().indexOf("XINZHE-WEB") > -1);
        if (access) {
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            ServletOutputStream servletOutputStream = null;
            try {
                if (StringUtil.isBlank(fileName)) {
                    fileName = file.getName();
                }
                int FILE_READ_SIZE = 1024;

                // 解决中文文件名乱码问题
                if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                    fileName = new String(fileName.getBytes(charset), Constant.Charset.ISO_8859_1);// firefox浏览器
                } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                    fileName = URLEncoder.encode(fileName, charset);// IE浏览器
                } else {
                    fileName = new String(fileName.getBytes(charset), Constant.Charset.ISO_8859_1);// firefox浏览器
                }

                response.reset();
                // 设置response的编码方式
                response.setContentType(charset);
                // response.setContentType("application/x-msdownload");
                response.setContentType("application/octet-stream");
                // response.setContentType("application/x-download");
                response.setContentLength((int) file.length());// 写明要下载的文件的大小
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");// 设置附加文件名
                response.setHeader("Connection", "close");
                response.setHeader("Content-Length", file.length() + "");

                // 读出文件到i/o流
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);

                // 从response对象中得到输出流,准备下载
                servletOutputStream = response.getOutputStream();

                byte[] bytes = new byte[FILE_READ_SIZE];// 相当于我们的缓存
                long downBytes = 0;// 该值用于计算当前实际下载了多少字节

                // 开始循环下载
                while (downBytes < file.length()) {
                    int readBytes = bufferedInputStream.read(bytes, 0, FILE_READ_SIZE);
                    downBytes += readBytes;
                    // 将b中的数据写到客户端的内存
                    servletOutputStream.write(bytes, 0, readBytes);
                }

                // 将写入到客户端的内存的数据,刷新到磁盘
                servletOutputStream.flush();

                servletOutputStream.close();
                bufferedInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            } finally {
                IOUtil.close(servletOutputStream);
                IOUtil.close(bufferedInputStream);
                IOUtil.close(fileInputStream);
            }
        } else {
            throw new UserAgentException("下载未授权");
        }
    }

    /**
     * 获得客户端操作系统
     *
     * @param request 请求对象
     * @return 返回客户端的操作系统名称。
     */
    public static final String getRemoteOS(HttpServletRequest request) {
        String result = null;
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null) {
            result = Constant.OS.name.UNKNOWN;
        } else {
            userAgent = userAgent.toLowerCase();
            if (userAgent.indexOf(Constant.OS.name.Linux.ALL.toLowerCase()) > -1) {
                if (userAgent.indexOf(Constant.OS.name.Android.ALL.toLowerCase()) > -1) {
                    result = Constant.OS.name.Android.ALL;
                } else {
                    result = Constant.OS.name.Linux.ALL;
                }
            } else if (userAgent.indexOf(Constant.OS.name.IOS.ALL.toLowerCase()) > -1) {
                result = Constant.OS.name.IOS.ALL;
            } else if (userAgent.indexOf(Constant.OS.name.Windows.ALL.toLowerCase()) > -1) {
                if (userAgent.indexOf("wow64") > -1) {
                    result = Constant.OS.name.Windows.X64;
                } else {
                    result = Constant.OS.name.Windows.X86;
                }
            } else {
                result = Constant.OS.name.UNKNOWN;
            }
        }
        return result;
    }

    /**
     * 获得客户端操作系统
     *
     * @param request 请求对象。
     * @return 返回客户端的操作系统编号。
     */
    public static final Integer getRemoteOSType(HttpServletRequest request) {
        Integer result = null;
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null) {
            result = Constant.OS.id.UNKNOWN;
        } else {
            userAgent = userAgent.toLowerCase();
            if (userAgent.indexOf(Constant.OS.name.Linux.ALL.toLowerCase()) > -1) {
                if (userAgent.indexOf(Constant.OS.name.Android.ALL.toLowerCase()) > -1) {
                    result = Constant.OS.id.Android.ALL;
                } else {
                    result = Constant.OS.id.Linux.ALL;
                }
            } else if (userAgent.indexOf(Constant.OS.name.IOS.ALL.toLowerCase()) > -1) {
                result = Constant.OS.id.IOS.ALL;
            } else if (userAgent.indexOf(Constant.OS.name.Windows.ALL.toLowerCase()) > -1) {
                if (userAgent.indexOf("wow64") > -1) {
                    result = Constant.OS.id.Windows.X64;
                } else {
                    result = Constant.OS.id.Windows.X86;
                }
            } else {
                result = Constant.OS.id.UNKNOWN;
            }
        }
        return result;
    }

    /**
     * 输出图片文件。
     *
     * @param imageFileName 图片文件的名称(包含路径)
     * @param formatName    a <code>String</code> containg the informal name of the
     *                      format.
     * @param response      响应对象。
     * @throws FileNotFoundException 异常
     * @throws IOException           异常
     */
    public static final void outputImage(String imageFileName, String formatName, ServletResponse response) throws FileNotFoundException, IOException {
        File imageFile = new File(imageFileName);
        outputImage(imageFile, formatName, response);
    }

    /**
     * 输出图片文件。
     *
     * @param imageFile  图片文件对象.
     * @param formatName a <code>String</code> containg the informal name of the
     *                   format.
     * @param response   响应对象。
     * @throws FileNotFoundException 异常
     * @throws IOException           异常
     */
    public static final void outputImage(File imageFile, String formatName, ServletResponse response) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        BufferedImage bufferedImage = ImageIO.read(fileInputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, formatName, servletOutputStream);
        servletOutputStream.close();
    }

    /**
     * 获得IP地址
     *
     * @param request 请求对象。
     * @return 返回请求的IP地址。
     */
    public static String getIP(HttpServletRequest request) {
        String sip = "";
        sip = request.getHeader("x-forwarded-for");
        if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
            sip = request.getHeader("proxy-Client-IP");
        }
        if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
            sip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
            sip = request.getRemoteAddr();
        }
        return sip;
    }

    /**
     * JS 简短路径(如：/js/jquery.js或jquery.js)变成完整路径(http://www.lizhaoweb.net/js/jquery.js)。
     *
     * @param accessURL 当前正在访问的页面。
     * @param content   要检索的文本。
     * @return 返回包含完整JS访问路径的文本。
     */
    public static String jsToComplete(URL accessURL, String content) {
        // while (true) {
        // Matcher matcher =
        // Pattern.compile("<script .*src=\"(?!http://)(?!https://)(.+)\"",
        // Pattern.CASE_INSENSITIVE).matcher(content);
        // if (matcher.find()) {
        // String matcheString = matcher.group(1);
        // String urlString = urlToHttpPath(accessURL, matcheString);
        // // content = content.replace(matcheString, urlString);
        //
        // StringBuffer stringBuffer = new StringBuffer();
        // while (matcher.find()) {
        // matcher.appendReplacement(stringBuffer, urlString);
        // }
        // matcher.appendTail(stringBuffer);
        // content = stringBuffer.toString();
        // } else {
        // break;
        // }
        // }

        Matcher matcher = Pattern.compile("(<script .*src=\")(?!http://)(?!https://)([^\"]*)(\")", Pattern.CASE_INSENSITIVE).matcher(content);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String matcheString = matcher.group(2);
            String urlString = matcher.group(1) + urlToHttpPath(accessURL, matcheString) + matcher.group(3);
            matcher.appendReplacement(stringBuffer, urlString);
        }
        matcher.appendTail(stringBuffer);
        content = stringBuffer.toString();
        return content;
    }

    /**
     * CSS 简短路径(如：/css/style.css或style.css)变成完整路径(http://www.lizhaoweb.net/css/
     * style.css)。
     *
     * @param accessURL 当前正在访问的页面。
     * @param content   要检索的文本。
     * @return 返回包含完整CSS访问路径的文本。
     */
    public static String cssToComplete(URL accessURL, String content) {
        // while (true) {
        // Matcher matcher =
        // Pattern.compile("<link .*href=\"(?!http://)(?!https://)(.+)\"",
        // Pattern.CASE_INSENSITIVE).matcher(content);
        // if (matcher.find()) {
        // String matcheString = matcher.group(1);
        // String urlString = urlToHttpPath(accessURL, matcheString);
        // // content = content.replace(matcheString, urlString);
        //
        // StringBuffer stringBuffer = new StringBuffer();
        // while (matcher.find()) {
        // matcher.appendReplacement(stringBuffer, urlString);
        // }
        // matcher.appendTail(stringBuffer);
        // content = stringBuffer.toString();
        // } else {
        // break;
        // }
        // }

        Matcher matcher = Pattern.compile("(<link .*href=\")(?!http://)(?!https://)([^\"]*)(\")", Pattern.CASE_INSENSITIVE).matcher(content);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String matcheString = matcher.group(2);
            String urlString = matcher.group(1) + urlToHttpPath(accessURL, matcheString) + matcher.group(3);
            matcher.appendReplacement(stringBuffer, urlString);
        }
        matcher.appendTail(stringBuffer);
        content = stringBuffer.toString();
        return content;
    }

    /**
     * IMG 简短路径(如：/image/ph.jpg或ph.jpg)变成完整路径(http://www.lizhaoweb.net/image/
     * ph.jpg)。
     *
     * @param accessURL 当前正在访问的页面。
     * @param content   要检索的文本。
     * @return 返回包含完整IMG访问路径的文本。
     */
    public static String imgToComplete(URL accessURL, String content) {
        // while (true) {
        // Matcher matcher =
        // Pattern.compile("(<img .*src=\")(?!http://)(?!https://)(.+)\"",
        // Pattern.CASE_INSENSITIVE).matcher(content);
        // if (matcher.find()) {
        // String matcheString = matcher.group(2);
        // String urlString = matcher.group(1) + urlToHttpPath(accessURL,
        // matcheString);
        // // content = content.replace(matcheString, urlString);
        //
        // StringBuffer stringBuffer = new StringBuffer();
        // matcher.appendReplacement(stringBuffer, urlString);
        // while (matcher.find()) {
        // matcher.appendReplacement(stringBuffer, urlString);
        // }
        // matcher.appendTail(stringBuffer);
        // content = stringBuffer.toString();
        // } else {
        // break;
        // }
        // }

        Matcher matcher = Pattern.compile("(<img .*src=\")(?!http://)(?!https://)([^\"]*)(\")", Pattern.CASE_INSENSITIVE).matcher(content);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String matcheString = matcher.group(2);
            String urlString = matcher.group(1) + urlToHttpPath(accessURL, matcheString) + matcher.group(3);
            matcher.appendReplacement(stringBuffer, urlString);
        }
        matcher.appendTail(stringBuffer);
        content = stringBuffer.toString();
        return content;
    }

    /**
     * FORM 简短路径 。
     *
     * @param accessURL 当前正在访问的页面。
     * @param content   要检索的文本。
     * @return 返回包含完整FORM访问路径的文本。
     */
    public static String formToComplete(URL accessURL, String content) {
        // while (true) {
        // Matcher matcher =
        // Pattern.compile("<form .*action=\"(?!http://)(?!https://)(.+)\"",
        // Pattern.CASE_INSENSITIVE).matcher(content);
        // if (matcher.find()) {
        // String matcheString = matcher.group(1);
        // String urlString = urlToHttpPath(accessURL, matcheString);
        // // content = content.replace(matcheString, urlString);
        //
        // StringBuffer stringBuffer = new StringBuffer();
        // while (matcher.find()) {
        // matcher.appendReplacement(stringBuffer, urlString);
        // }
        // matcher.appendTail(stringBuffer);
        // content = stringBuffer.toString();
        // } else {
        // break;
        // }
        // }

        Matcher matcher = Pattern.compile("(<form .*action=\")(?!http://)(?!https://)([^\"]*)(\")", Pattern.CASE_INSENSITIVE).matcher(content);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String matcheString = matcher.group(2);
            String urlString = matcher.group(1) + urlToHttpPath(accessURL, matcheString) + matcher.group(3);
            matcher.appendReplacement(stringBuffer, urlString);
        }
        matcher.appendTail(stringBuffer);
        content = stringBuffer.toString();
        return content;
    }

    /**
     * 替换文本中的字符。
     *
     * @param key     要替换的键。它会替换同${key}中的值。
     * @param value   值。
     * @param content 文本。
     * @return 返回替换扔到的字符串。
     */
    public static String replace(String key, String value, String content) {
        if (content == null) {
            return null;
        }
        String tempContent = content;
        String realKey = String.format("${%s}", key);
        while (tempContent.indexOf(realKey) > -1) {
            tempContent = tempContent.replace(realKey, value);
        }
        return tempContent;
    }

    /**
     * 去除字符串的和空格。
     *
     * @param content 字符串。
     * @return 返回去除后的字符串。
     */
    public static String removeSpaceAndNewLine(String content) {
        String tempContent = content;
        tempContent = StringUtil.removeNewLine(tempContent);
        tempContent = removeBlank(tempContent);
        return tempContent;
    }

    /**
     * 将字符串形式的请求参数，转换为 Map 形式的请求参数。
     *
     * @param stringParameters 字符串形式的请求参数
     * @return Map
     */
    public static Map<String, String[]> stringParametersToMapParameters(String stringParameters) {
        if (stringParameters == null) {
            return null;
        }
        Map<String, String[]> mapParameters = new HashMap<String, String[]>();

        if (stringParameters.trim().length() > 0) {
            String[] parameterNameAndValues = stringParameters.split("&");
            for (String parameterNameAndValue : parameterNameAndValues) {
                String[] parameterNameAndValueSplist = parameterNameAndValue.split("=");
                if (parameterNameAndValueSplist.length < 1) {
                    continue;
                }
                String[] parameterValues = mapParameters.get(parameterNameAndValueSplist[0]);
                String parameterValueTemp = "";
                if (parameterNameAndValueSplist.length == 2) {
                    parameterValueTemp = parameterNameAndValueSplist[1];
                } else if (parameterNameAndValueSplist.length > 2) {
                    parameterValueTemp = parameterNameAndValueSplist[1];
                }
                try {
                    parameterValueTemp = URLEncoder.encode(parameterValueTemp, Charset.UTF8);
                } catch (UnsupportedEncodingException e) {
                }
                if (parameterValues != null) {
                    parameterValues = ArrayUtil.add(parameterValues, parameterValueTemp);
                } else {
                    parameterValues = ArrayUtil.add(new String[0], parameterValueTemp);
                }
                mapParameters.put(parameterNameAndValueSplist[0], parameterValues);
            }
        }
        return mapParameters;
    }

    /**
     * 去除空格。
     *
     * @param content 文本内容
     * @return String
     */
    public static String removeBlank(String content) {
        if (content == null) {
            return null;
        } else if ("".equals(content.trim())) {
            return "";
        }
        String tempContent = content.trim();
        while (tempContent.indexOf("\t") > -1) {
            tempContent = tempContent.replace("\t", " ");
        }
        while (tempContent.indexOf("  ") > -1) {
            tempContent = tempContent.replace("  ", " ");
        }
        while (tempContent.indexOf("> ") > -1) {
            tempContent = tempContent.replace("> ", ">");
        }
        while (tempContent.indexOf(" <") > -1) {
            tempContent = tempContent.replace(" <", "<");
        }
        while (tempContent.indexOf("=\" ") > -1) {
            tempContent = tempContent.replace("=\" ", "=\"");
        }
        return tempContent;
    }

    /**
     * 打印请求头
     *
     * @param request 请求对象
     */
    public static void printRequestHeaders(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        System.out.println("/===================== HTTP Request Header =====================\\");
        Enumeration<?> headerNameEnumeration = request.getHeaderNames();
        while (headerNameEnumeration.hasMoreElements()) {
            Object headerNameEnumerationO = headerNameEnumeration.nextElement();
            if (headerNameEnumerationO != null && headerNameEnumerationO instanceof String) {
                Enumeration<?> headerValueEnumeration = request.getHeaders((String) headerNameEnumerationO);
                System.out.println(String.format("\t%s : ", headerNameEnumerationO));
                while (headerValueEnumeration.hasMoreElements()) {
                    Object object = headerValueEnumeration.nextElement();
                    System.out.println(String.format("\t\t%s", object));
                }
            }
        }
        System.out.println("\\===================== HTTP Request Header =====================/");
    }

    /**
     * 打印请求属性
     *
     * @param request 请求对象
     */
    public static void printRequestAttributes(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        System.out.println("/===================== HTTP Request Attribute =====================\\");
        Enumeration<?> attributeNameEnumeration = request.getAttributeNames();
        while (attributeNameEnumeration.hasMoreElements()) {
            Object attributeNameEnumerationO = attributeNameEnumeration.nextElement();
            if (attributeNameEnumerationO != null && attributeNameEnumerationO instanceof String) {
                System.out.println(String.format("\t%s : ", attributeNameEnumerationO));
                Object object = request.getAttribute((String) attributeNameEnumerationO);
                System.out.println(String.format("\t\t%s", object));
            }
        }
        System.out.println("\\===================== HTTP Request Attribute =====================/");
    }

    /**
     * 打印请求属性
     *
     * @param request 请求对象
     */
    public static void printRequestParameters(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        System.out.println("/===================== HTTP Request Parameter =====================\\");
        Enumeration<?> parameterNameEnumeration = request.getParameterNames();
        while (parameterNameEnumeration.hasMoreElements()) {
            Object parameterNameEnumerationO = parameterNameEnumeration.nextElement();
            if (parameterNameEnumerationO != null && parameterNameEnumerationO instanceof String) {
                String[] parameterValueArray = request.getParameterValues((String) parameterNameEnumerationO);
                System.out.println(String.format("\t%s : ", parameterNameEnumerationO));
                for (String parameterValue : parameterValueArray) {
                    System.out.println(String.format("\t\t%s", parameterValue));
                }
            }
        }
        System.out.println("\\===================== HTTP Request Parameter =====================/");
    }

    /**
     * 获取请求体
     *
     * @param request 请求对象
     * @return 返回请求体内容。
     */
    public static String getRequestBody(HttpServletRequest request) {
        String charset = request.getCharacterEncoding();
        if (StringUtil.isBlank(charset)) {
            charset = Constant.Charset.UTF8;
        }
        try {
            return IOUtil.toString(request.getInputStream(), charset);
        } catch (Exception e) {
            return null;
        }
    }


    // 内部HTTP URL路径转换为外部HTTP URL路径。
    private static String urlToHttpPath(URL accessURL, String matcheString) {
        String urlString = null;
        if (matcheString.startsWith("/")) {
            if (accessURL.getPort() < 0) {
                urlString = String.format("%s://%s/%s", accessURL.getProtocol(), accessURL.getHost(), matcheString);
            } else {
                urlString = String.format("%s://%s:%d/%s", accessURL.getProtocol(), accessURL.getHost(), accessURL.getPort(), matcheString);
            }
        } else {
            String queryString = accessURL.getPath();
            int lastIndex = queryString.lastIndexOf("/");
            if (lastIndex > -1) {
                queryString = queryString.substring(0, lastIndex);
            }
            if (accessURL.getPort() < 0) {
                urlString = String.format("%s://%s/%s/%s", accessURL.getProtocol(), accessURL.getHost(), queryString, matcheString);
            } else {
                urlString = String.format("%s://%s:%d/%s/%s", accessURL.getProtocol(), accessURL.getHost(), accessURL.getPort(), queryString, matcheString);
            }
        }
        urlString = formatPath(urlString);
        return urlString;
    }
}
