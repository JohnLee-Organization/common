/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file.download.by_url
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:13
 */
package net.lizhaoweb.common.file.download.http_url;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.lizhaoweb.common.file.GenericRequest;
import net.lizhaoweb.common.file.download.FileObject;
import net.lizhaoweb.common.file.download.FileObjectMetadata;
import net.lizhaoweb.common.file.download.GetFileObjectRequest;
import net.lizhaoweb.common.file.download.IFileObjectOperator;
import net.lizhaoweb.common.file.event.ProgressEventType;
import net.lizhaoweb.common.file.event.ProgressInputStream;
import net.lizhaoweb.common.file.event.ProgressListener;
import net.lizhaoweb.common.file.event.ProgressPublisher;
import net.lizhaoweb.common.file.utils.CRC64;
import net.lizhaoweb.common.file.utils.CodingUtils;
import net.lizhaoweb.common.file.utils.DownloadFileConstants;
import net.lizhaoweb.common.file.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.CheckedInputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class FileObjectOperator implements IFileObjectOperator {

    @Setter
    private int connectTimeout = 30000;

    @Setter
    private int readTimeout = 5000;

    @Setter
    private String endPoint;

    @Override
    public FileObjectMetadata getFileObjectMeta(GenericRequest genericRequest) {
        CodingUtils.assertParameterNotNull(genericRequest, "genericRequest");
        String uri = genericRequest.getUri();
        String queryString = genericRequest.getQueryString();
        CodingUtils.assertParameterNotNull(uri, "uri");
        CodingUtils.assertParameterNotNull(queryString, "queryString");

        HttpURLConnection connection = null;
        try {
            Map<String, String> requestHeaderMap = new HashMap<>();
            connection = new HttpURLClient(this.endPoint, this.connectTimeout, this.readTimeout).send(genericRequest, "HEAD", requestHeaderMap);
            return this.setFileObjectMeta(connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeHttpURLConnection(connection);
        }
    }

    @Override
    public FileObject getFileObject(GetFileObjectRequest getFileObjectRequest) {
        CodingUtils.assertParameterNotNull(getFileObjectRequest, "getFileObjectRequest");
        String uri = getFileObjectRequest.getUri();
        String queryString = getFileObjectRequest.getQueryString();
        CodingUtils.assertParameterNotNull(uri, "uri");
        CodingUtils.assertParameterNotNull(queryString, "queryString");

        ProgressListener listener = getFileObjectRequest.getProgressListener();
        ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_STARTED_EVENT);

        return getFileObject(getFileObjectRequest, 1, 3);
    }

    private FileObject getFileObject(GetFileObjectRequest getFileObjectRequest, int tryCount, int maxCount) {
        FileObject fileObject = new FileObject();
        ProgressListener listener = getFileObjectRequest.getProgressListener();
        HttpURLConnection connection = null;
        try {
            fileObject.setUri(getFileObjectRequest.getUri());
            fileObject.setQueryString(getFileObjectRequest.getQueryString());

            Map<String, String> requestHeaderMap = new HashMap<>();
            if (getFileObjectRequest.getRange() != null) {
                long startPos = getFileObjectRequest.getRange()[0];
                long endPos = getFileObjectRequest.getRange()[1];
                requestHeaderMap.put("Range", "bytes=" + startPos + "-" + endPos);
            }

            connection = new HttpURLClient(this.endPoint, this.connectTimeout, this.readTimeout).send(getFileObjectRequest, "GET", requestHeaderMap);
            fileObject.setObjectMetadata(this.setFileObjectMeta(connection));

            InputStream inputStream = connection.getInputStream();
            ProgressInputStream progressInputStream = new ProgressInputStream(inputStream, listener) {
                @Override
                protected void onEOF() {
                    ProgressPublisher.publishProgress(getListener(), ProgressEventType.TRANSFER_COMPLETED_EVENT);
                }
            };
            CRC64 crc = new CRC64();
            CheckedInputStream checkedInputstream = new CheckedInputStream(progressInputStream, crc);
            fileObject.setObjectContent(checkedInputstream);

//            byte[] contentBytes = this.readStream(connection.getInputStream());
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
//            fileObject.setObjectContent(byteArrayInputStream);
        } catch (IOException e) {
            if (tryCount > maxCount) {
                ProgressPublisher.publishProgress(listener, ProgressEventType.TRANSFER_FAILED_EVENT);
                throw new RuntimeException(e);
            }
            fileObject = this.getFileObject(getFileObjectRequest, ++tryCount, maxCount);
        } finally {
//            this.closeHttpURLConnection(connection);
        }
        return fileObject;
    }

    private byte[] readStreamAsByteArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
//            byte[] buffer = new byte[1024];
//            int len = -1;
//            while ((len = inputStream.read(buffer)) != -1) {
//                outStream.write(buffer, 0, len);
//            }
//            outStream.flush();
            IOUtils.copy(inputStream, outStream, 1024);
        } finally {
//            outStream.close();
//            inputStream.close();
            IOUtils.close(outStream);
            IOUtils.close(inputStream);
        }
        return outStream.toByteArray();
    }

    private FileObjectMetadata setFileObjectMeta(HttpURLConnection connection) {
        FileObjectMetadata fileObjectMeta = new FileObjectMetadata();
        Map<String, List<String>> headerMap = connection.getHeaderFields();

        List<String> ETagValue = headerMap.get("ETag");
        if (ETagValue != null && ETagValue.size() > 0) {
            String value = ETagValue.get(0);
            fileObjectMeta.setETag(value);
        }

        List<String> ObjectTypeValue = headerMap.get("x-oss-object-type");
        if (ObjectTypeValue != null && ObjectTypeValue.size() > 0) {
            String value = ObjectTypeValue.get(0);
            fileObjectMeta.setObjectType(value);
        }

        List<String> ContentMD5Value = headerMap.get("Content-MD5");
        if (ContentMD5Value != null && ContentMD5Value.size() > 0) {
            String value = ContentMD5Value.get(0);
            fileObjectMeta.setContentMD5(value);
        }

        List<String> ContentTypeValue = headerMap.get("Content-Type");
        if (ContentTypeValue != null && ContentTypeValue.size() > 0) {
            String value = ContentTypeValue.get(0);
            fileObjectMeta.setContentType(value);
        }

        List<String> ContentEncodingValue = headerMap.get("Content-Encoding");
        if (ContentEncodingValue != null && ContentEncodingValue.size() > 0) {
            String value = ContentEncodingValue.get(0);
            fileObjectMeta.setContentEncoding(value);
        }

        List<String> CacheControlValue = headerMap.get("Cache-Control");
        if (CacheControlValue != null && CacheControlValue.size() > 0) {
            String value = CacheControlValue.get(0);
            fileObjectMeta.setCacheControl(value);
        }

        List<String> ContentDispositionValue = headerMap.get("Content-Disposition");
        if (ContentDispositionValue != null && ContentDispositionValue.size() > 0) {
            String value = ContentDispositionValue.get(0);
            fileObjectMeta.setContentDisposition(value);
        }

        List<String> ServerSideEncryptionValue = headerMap.get("x-oss-server-side-encryption");
        if (ServerSideEncryptionValue != null && ServerSideEncryptionValue.size() > 0) {
            String value = ServerSideEncryptionValue.get(0);
            fileObjectMeta.setServerSideEncryption(value);
        }

        List<String> ServerSideEncryptionKeyIdValue = headerMap.get("x-oss-server-side-encryption-key-id");
        if (ServerSideEncryptionKeyIdValue != null && ServerSideEncryptionKeyIdValue.size() > 0) {
            String value = ServerSideEncryptionKeyIdValue.get(0);
            fileObjectMeta.setServerSideEncryptionKeyId(value);
        }

        List<String> RequestIdValue = headerMap.get("x-oss-request-id");
        if (RequestIdValue != null && RequestIdValue.size() > 0) {
            String value = RequestIdValue.get(0);
            fileObjectMeta.setRequestId(value);
        }

        List<String> ContentLengthValue = headerMap.get("Content-Length");
        if (ContentLengthValue != null && ContentLengthValue.size() > 0) {
            String value = ContentLengthValue.get(0);
            fileObjectMeta.setSize(Long.valueOf(value));
        }

        List<String> LastModifiedValue = headerMap.get("Last-Modified");
        if (LastModifiedValue != null && LastModifiedValue.size() > 0) {
            String value = LastModifiedValue.get(0);
            fileObjectMeta.setLastModified(new Date(value));
        }

        List<String> ExpirationTimeValue = headerMap.get("Expires");
        if (ExpirationTimeValue != null && ExpirationTimeValue.size() > 0) {
            String value = ExpirationTimeValue.get(0);
            fileObjectMeta.setExpirationTime(new Date(value));
        }
        return fileObjectMeta;
    }

    private void closeHttpURLConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }
}

@RequiredArgsConstructor
class HttpURLClient {

    private static Pattern HTTP_PROTOCOL = Pattern.compile("^HTTPS?://", Pattern.CASE_INSENSITIVE);

    @NonNull
    private String endPoint;

    @NonNull
    private int connectTimeout;

    @NonNull
    private int readTimeout;

    HttpURLConnection send(GenericRequest genericRequest, String method, Map<String, String> headerMap) throws IOException {
        URL url = this.getUrl(genericRequest.getUri(), genericRequest.getQueryString());
        return this.send(url, method, headerMap);
    }

    private HttpURLConnection send(URL url, String method, Map<String, String> headerMap) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        // 发送POST请求必须设置如下两行
        connection.setDoInput(true);// 创建输入流，必须有
        connection.setDoOutput(true);// 创建输出流，必须有
        connection.setUseCaches(false);// 不缓存
        connection.setConnectTimeout(connectTimeout);// 连接超时
        connection.setReadTimeout(readTimeout);

        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Charset", DownloadFileConstants.DEFAULT_CHARSET_NAME);
        connection.setRequestProperty("Charset", DownloadFileConstants.DEFAULT_CHARSET_NAME);
        connection.setRequestProperty("ContentType", DownloadFileConstants.DEFAULT_CHARSET_NAME);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Connection", "Keep-Alive");// 连接方式，此处为长连接
        connection.setRequestProperty("User-Agent", "Java savor http download");

        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                String headerName = entry.getKey();
                String headerValue = entry.getValue();
                if (headerValue == null) {
                    continue;
                }
                if ("Accept".equalsIgnoreCase(headerName) || "Accept-Charset".equalsIgnoreCase(headerName) || "Charsert".equalsIgnoreCase(headerName) || "Content-Type".equalsIgnoreCase(headerName) || "ContentType".equalsIgnoreCase(headerName) || "Connection".equalsIgnoreCase(headerName) || "User-Agent".equalsIgnoreCase(headerName)) {
                    connection.setRequestProperty(headerName, headerValue);
                } else {
                    connection.addRequestProperty(headerName, headerValue);
                }
            }
        }

        connection.connect();
        return connection;
    }

    private URL getUrl(String uri, String queryString) throws MalformedURLException {
        String url, realEndPoint, realUri, realQueryString;

        realEndPoint = this.endPoint.trim();
        if (!HTTP_PROTOCOL.matcher(realEndPoint).find()) {
            realEndPoint = "http://" + this.endPoint;
        }
        if (uri == null) {
            realUri = "";
        } else if (uri.trim().endsWith("?")) {
            realUri = uri.trim().substring(0, uri.trim().length() - 1);
        } else {
            realUri = uri.trim();
        }
        if (queryString == null) {
            realQueryString = "";
        } else if (queryString.trim().startsWith("?")) {
            realQueryString = queryString.trim().substring(1);
        } else {
            realQueryString = queryString.trim();
        }

        if (realQueryString.length() < 1) {
            url = String.format("%s/%s", realEndPoint, realUri);
        } else {
            url = String.format("%s/%s?%s", realEndPoint, realUri, realQueryString);
        }
        return new URL(url);
    }
}
