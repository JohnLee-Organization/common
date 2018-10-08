package net.lizhaoweb.common.util.http.common;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 请求配置类
 *
 * @author arron
 * @version 1.0.0.0.1
 * @notes Created on 2016-2-2 15:14:32<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class HttpConfig {

    private HttpConfig() {
    }


    /**
     * 获取实例
     *
     * @return HttpConfig
     */
    public static HttpConfig custom() {
        return new HttpConfig();
    }

    /**
     * HttpClient对象
     */
    private HttpClient client;

    /**
     * CloseableHttpAsyncClient对象
     */
    private CloseableHttpAsyncClient asyncClient;

    /**
     * 资源url
     */
    private String url;

    /**
     * 请求头信息
     */
    private Header[] requestHeaders;

    /**
     * 是否返回response的headers
     */
    private boolean returnRespHeaders;

    /**
     * 请求方法
     */
    private HttpMethods method = HttpMethods.GET;

    /**
     * 请求方法名称
     */
    private String methodName;

    /**
     * 用于cookie操作
     */
    private HttpContext context;

    /**
     * 请求参数
     */
    private Map<String, ?> requestParameterMap;

    /**
     * 输入输出编码
     */
    private String encoding = Charset.defaultCharset().displayName();

    /**
     * 输入编码
     */
    private String inEnc;


    /**
     * 输出编码
     */
    private String outEnc;

    /**
     * 输出流对象
     */
    private OutputStream out;

    /**
     * 异步操作回调执行器
     */
    private IHandler handler;

    /**
     * 响应头信息
     */
    private Header[] responseHeaders;

    /**
     * HttpClient对象
     */
    public HttpConfig client(HttpClient client) {
        this.client = client;
        return this;
    }

    public HttpClient client() {
        return client;
    }

    /**
     * CloseableHttpAsyncClient对象
     */
    public HttpConfig asyncClient(CloseableHttpAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
        return this;
    }

    public CloseableHttpAsyncClient asyncClient() {
        return asyncClient;
    }

    /**
     * 资源url
     */
    public HttpConfig url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return url;
    }

    /**
     * 设置请求头信息
     */
    public HttpConfig requestHeaders(Header[] requestHeaders) {
        return this.requestHeaders(requestHeaders, true);
    }

    /**
     * 设置请求头信息(是否返回response中的headers)
     */
    public HttpConfig requestHeaders(Header[] requestHeaders, boolean returnResponseHeaders) {
        this.requestHeaders = requestHeaders;
        this.returnRespHeaders = returnResponseHeaders;
        return this;
    }

    public Header[] requestHeaders() {
        return requestHeaders;
    }

    /**
     * 设置响应头信息
     */
    public HttpConfig responseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
        return this;
    }

    public Header[] responseHeaders() {
        return responseHeaders;
    }

    /**
     * 请求方法
     */
    public HttpConfig method(HttpMethods method) {
        this.method = method;
        this.methodName(method.getName());
        return this;
    }

    public HttpMethods method() {
        return method;
    }

    /**
     * 请求方法
     */
    private HttpConfig methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    private String methodName() {
        return methodName;
    }

    /**
     * cookie操作相关
     */
    public HttpConfig context(HttpContext context) {
        this.context = context;
        return this;
    }

    public HttpContext context() {
        return context;
    }

    /**
     * 传递参数
     */
    public HttpConfig requestParameters(Map<String, ?> requestParameterMap) {
        this.requestParameterMap = requestParameterMap;
        return this;
    }

    public Map<String, ?> requestParameters() {
        return requestParameterMap;
    }

    /**
     * 输入输出编码
     */
    public HttpConfig encoding(String encoding) {
        // 设置输入输出
        inEnc(encoding);
        outEnc(encoding);
        this.encoding = encoding;
        return this;
    }

    public String encoding() {
        return encoding;
    }

    /**
     * 输入编码
     */
    public HttpConfig inEnc(String inEnc) {
        this.inEnc = inEnc;
        return this;
    }

    public String inEnc() {
        return inEnc == null ? encoding : inEnc;
    }

    /**
     * 输出编码
     */
    public HttpConfig outEnc(String outEnc) {
        this.outEnc = outEnc;
        return this;
    }

    public String outEnc() {
        return outEnc == null ? encoding : outEnc;
    }

    /**
     * 输出流对象
     */
    public HttpConfig out(OutputStream out) {
        this.out = out;
        return this;
    }

    public OutputStream out() {
        return out;
    }

    /**
     * 异步操作回调执行器
     */
    public HttpConfig handler(IHandler handler) {
        this.handler = handler;
        return this;
    }

    public IHandler handler() {
        return handler;
    }


    public boolean isReturnRespHeaders() {
        return returnRespHeaders;
    }


    @Override
    public String toString() {
        String newLine = "\n";

        StringBuffer string = new StringBuffer(newLine).append("HTTP-INFO : [").append(newLine);
        string.append(">>>>>>>>>>>>>>>>>>>> HTTP-Request >>>>>>>>>>>>>>>>>>>>").append(newLine);
        string.append("\tURL : ").append(this.url()).append(newLine);
        string.append("\tMethod : ").append(this.methodName()).append(newLine);
        string.append("\tEncoding : ").append(this.inEnc()).append(newLine);
        Header[] requestHeaders = this.requestHeaders();
        if (requestHeaders != null) {
            string.append("\tHeaders : ").append(newLine);
            for (Header header : requestHeaders) {
                string.append("\t\t").append(header.getName()).append("=").append(header.getValue()).append(newLine);
            }
        }
        Map<String, ?> requestParameterMap = this.requestParameters();
        if (requestParameterMap != null) {
            string.append("\tParameters : ").append(newLine);
            for (Map.Entry<String, ?> entry : requestParameterMap.entrySet()) {
                string.append("\t\t").append(entry.getKey()).append("=").append(entry.getValue()).append(newLine);
            }
        }


        string.append("<<<<<<<<<<<<<<<<<<<< HTTP-Response <<<<<<<<<<<<<<<<<<<<").append(newLine);
        string.append("\tEncoding : ").append(this.outEnc()).append(newLine);
        Header[] responseHeaders = this.responseHeaders();
        if (responseHeaders != null) {
            string.append("\tHeaders : ").append(newLine);
            for (Header header : responseHeaders) {
                string.append("\t\t").append(header.getName()).append("=").append(header.getValue()).append(newLine);
            }
        }

        return string.append(newLine).append("]").append(newLine).toString();
    }
}