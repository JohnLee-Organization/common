package net.lizhaoweb.common.util.http.httpclient;

import net.lizhaoweb.common.util.http.common.HttpConfig;
import net.lizhaoweb.common.util.http.common.HttpMethods;
import net.lizhaoweb.common.util.http.common.IHandler;
import net.lizhaoweb.common.util.http.common.Utils;
import net.lizhaoweb.common.util.http.exception.HttpProcessException;
import net.lizhaoweb.common.util.http.httpclient.builder.HACB;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author arron
 * @version 1.0
 * @date 2015年11月4日 下午10:12:11
 */
public class HttpAsyncClientUtil extends AbstractHttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpAsyncClientUtil.class);

    /**
     * 创建client对象 并设置全局的标准cookie策略
     *
     * @return
     * @throws HttpProcessException
     */
    public static CloseableHttpAsyncClient create(String url) throws HttpProcessException {
        if (url.toLowerCase().startsWith("https://")) {
            return HACB.custom().ssl().build();
        } else {
            return HACB.custom().build();
        }
    }

    /**
     * 请求资源或服务
     *
     * @param config
     * @throws HttpProcessException
     */
    public static void send(HttpConfig config) throws HttpProcessException {
        execute(config);
    }

    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------

    /**
     * 以Get方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void get(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.GET).asyncClient(client).url(url).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void get(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.GET));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void post(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.POST).asyncClient(client).url(url).requestParameters(parasMap).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void post(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.POST));
    }

    /**
     * 以Put方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void put(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.PUT).asyncClient(client).url(url).requestParameters(parasMap).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Put方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void put(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.PUT));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void delete(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.DELETE).asyncClient(client).url(url).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void delete(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.DELETE));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void patch(CloseableHttpAsyncClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.PATCH).asyncClient(client).url(url).requestParameters(parasMap).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void patch(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.PATCH));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void head(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.HEAD).asyncClient(client).url(url).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void head(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.HEAD));
    }

    /**
     * 以Options方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void options(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.OPTIONS).asyncClient(client).url(url).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Options方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void options(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.OPTIONS));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @param handler  回调处理对象
     * @throws HttpProcessException
     */
    public static void trace(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, String encoding, IHandler handler) throws HttpProcessException {
        send(HttpConfig.custom().method(HttpMethods.TRACE).asyncClient(client).url(url).requestHeaders(headers).context(context).encoding(encoding).handler(handler));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void trace(HttpConfig config) throws HttpProcessException {
        send(config.method(HttpMethods.TRACE));
    }

    /**
     * 下载图片
     *
     * @param client  client对象
     * @param url     资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @param out     输出流
     * @throws HttpProcessException
     */
    public static void down(CloseableHttpAsyncClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
        execute(HttpConfig.custom().method(HttpMethods.GET).asyncClient(client).url(url).requestHeaders(headers).context(context).out(out));
    }

    /**
     * 下载图片
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    public static void down(HttpConfig config) throws HttpProcessException {
        execute(config.method(HttpMethods.GET));
    }

    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------


    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @throws HttpProcessException
     */
    private static void execute(final HttpConfig config) throws HttpProcessException {
        if (config.asyncClient() == null) {// 检测是否设置了client
            config.asyncClient(create(config.url()));
        }
        try {
            // 创建请求对象
            HttpRequestBase request = getRequest(config.url(), config.method());

            // 设置header信息
            request.setHeaders(config.requestHeaders());

            // 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                List<NameValuePair> nvps = new ArrayList<>();

                // 检测url中是否存在参数
                config.url(Utils.checkHasParas(config.url(), nvps, config.inEnc()));

                // 装填参数
                HttpEntity entity = Utils.map2List(nvps, config.requestParameters(), config.inEnc());

                // 设置参数到请求对象中
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);

                logger.info("请求地址：" + config.url());
                logger.info("请求参数：" + nvps.toString());
            } else {
                int idx = config.url().indexOf("?");
                logger.info("请求地址：" + config.url().substring(0, (idx > 0 ? idx : config.url().length())));
                if (idx > 0) {
                    logger.info("请求参数：" + config.url().substring(idx + 1));
                }
            }
            // 执行请求
            final CloseableHttpAsyncClient client = config.asyncClient();
            final IHandler handler = config.handler();

            // Start the client
            client.start();
            // 异步执行请求操作，通过回调，处理结果
            client.execute(request, new FutureCallback<HttpResponse>() {
                @Override
                public void failed(Exception e) {
                    handler.failed(e);
                    close(client);
                }

                @Override
                public void completed(HttpResponse resp) {
                    try {
                        if (config.out() == null) {
                            handler.completed(fmt2String(config, resp));
                        } else {
                            handler.down(fmt2Stream(config, resp));
                        }
                    } catch (HttpProcessException e) {
                        e.printStackTrace();
                    }
                    close(client);
                }

                @Override
                public void cancelled() {
                    handler.cancelled();
                    close(client);
                }
            });

        } catch (Exception e) {
            String exceptionString = String.format("%s\n%s", e.getMessage(), config.toString());
            throw new HttpProcessException(exceptionString, e);
        }
    }


    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------


    /**
     * 转化为字符串
     *
     * @param config   请求参数配置
     * @param response 响应对象
     * @return
     * @throws HttpProcessException
     */
    private static String fmt2String(HttpConfig config, HttpResponse response) throws HttpProcessException {
        String body = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                final InputStream inStream = entity.getContent();
                try {
                    final StringBuilder sb = new StringBuilder();
                    final char[] tmp = new char[1024];
                    final Reader reader = new InputStreamReader(inStream, config.outEnc());
                    int l;
                    while ((l = reader.read(tmp)) != -1) {
                        sb.append(tmp, 0, l);
                    }
                    body = sb.toString();
                } finally {
                    inStream.close();
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            String exceptionString = String.format("%s\n%s", e.getMessage(), config.toString());
            throw new HttpProcessException(exceptionString, e);
        }
        return body;
    }

    /**
     * 关闭client对象
     *
     * @param client
     */
    private static void close(final CloseableHttpAsyncClient client) {
        try {
            client.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
}