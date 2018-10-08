package net.lizhaoweb.common.util.http.httpclient;

import net.lizhaoweb.common.util.http.common.HttpConfig;
import net.lizhaoweb.common.util.http.common.HttpMethods;
import net.lizhaoweb.common.util.http.common.Utils;
import net.lizhaoweb.common.util.http.exception.HttpProcessException;
import net.lizhaoweb.common.util.http.httpclient.builder.HCB;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用HttpClient模拟发送（http/https）请求
 *
 * @author arron
 * @version 1.0
 * @date 2015年11月4日 下午4:10:59
 */
public class HttpClientUtil extends AbstractHttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // 默认采用的http协议的HttpClient对象
    private static HttpClient client4HTTP;

    // 默认采用的https协议的HttpClient对象
    private static HttpClient client4HTTPS;

    static {
        try {
            client4HTTP = HCB.custom().build();
            client4HTTPS = HCB.custom().ssl().build();
        } catch (HttpProcessException e) {
            logger.error("创建https协议的HttpClient对象出错：{}", e);
        }
    }

    /**
     * 判断url是http还是https，直接返回相应的默认client对象
     *
     * @return 返回对应默认的client对象
     * @throws HttpProcessException
     */
    private static HttpClient create(String url) throws HttpProcessException {
        if (url.toLowerCase().startsWith("https://")) {
            return client4HTTPS;
        } else {
            return client4HTTP;
        }
    }

    /**
     * 请求资源或服务
     *
     * @param config
     * @return
     * @throws HttpProcessException
     */
    public static String send(HttpConfig config) throws HttpProcessException {
        return fmt2String(config, execute(config));
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
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String get(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.GET).requestHeaders(headers).context(context).encoding(encoding));
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String get(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.GET));
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
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String post(HttpClient client, String url, Header[] headers, Map<String, Object> parasMap, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.POST).requestHeaders(headers).requestParameters(parasMap).context(context).encoding(encoding));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String post(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.POST));
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
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String put(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.PUT).requestHeaders(headers).requestParameters(parasMap).context(context).encoding(encoding));
    }

    /**
     * 以Put方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String put(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.PUT));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String delete(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.DELETE).requestHeaders(headers).context(context).encoding(encoding));
    }

    /**
     * 以Delete方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String delete(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.DELETE));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String patch(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.PATCH).requestHeaders(headers).requestParameters(parasMap).context(context).encoding(encoding));
    }

    /**
     * 以Patch方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String patch(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.PATCH));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String head(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.HEAD).requestHeaders(headers).context(context).encoding(encoding));
    }

    /**
     * 以Head方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String head(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.HEAD));
    }

    /**
     * 以Options方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String options(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.OPTIONS).requestHeaders(headers).context(context).encoding(encoding));
    }

    /**
     * 以Options方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String options(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.OPTIONS));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static String trace(HttpClient client, String url, Header[] headers, HttpContext context, String encoding) throws HttpProcessException {
        return send(HttpConfig.custom().client(client).url(url).method(HttpMethods.TRACE).requestHeaders(headers).context(context).encoding(encoding));
    }

    /**
     * 以Trace方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return
     * @throws HttpProcessException
     */
    public static String trace(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.TRACE));
    }

    /**
     * 下载图片
     *
     * @param client  client对象
     * @param url     资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @param out     输出流
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static OutputStream down(HttpClient client, String url, Header[] headers, HttpContext context, OutputStream out) throws HttpProcessException {
        HttpConfig config = HttpConfig.custom().client(client).url(url).method(HttpMethods.GET).requestHeaders(headers).context(context).out(out);
        return fmt2Stream(config, execute(config));
    }

    /**
     * 下载图片
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    public static OutputStream down(HttpConfig config) throws HttpProcessException {
        HttpConfig httpConfig = config.method(HttpMethods.GET);
        return fmt2Stream(httpConfig, execute(httpConfig));
    }

    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------
    // -----------华----丽----分----割----线--------------

    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws HttpProcessException
     */
    private static HttpResponse execute(HttpConfig config) throws HttpProcessException {
        HttpResponse resp = null;
        try {
            if (config.client() == null) {// 检测是否设置了client
                config.client(create(config.url()));
            }

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
                if (nvps.size() > 0) {
                    logger.info("请求参数：" + nvps.toString());
                }
            } else {
                int idx = config.url().indexOf("?");
                logger.info("请求地址：" + config.url().substring(0, (idx > 0 ? idx : config.url().length())));
                if (idx > 0) {
                    logger.info("请求参数：" + config.url().substring(idx + 1));
                }
            }

            // 执行请求操作，并拿到结果（同步阻塞）
            resp = (config.context() == null) ? config.client().execute(request) : config.client().execute(request, config.context());

            if (config.isReturnRespHeaders()) {
                // 获取所有response的header信息
                config.responseHeaders(resp.getAllHeaders());
            }

            // 获取结果实体
            return resp;
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
            if (response.getEntity() != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(response.getEntity(), config.outEnc());
                logger.debug(body);
            }
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            String exceptionString = String.format("%s\n%s", e.getMessage(), config.toString());
            throw new HttpProcessException(exceptionString, e);
        } finally {
            close(response);
        }
        return body;
    }
}