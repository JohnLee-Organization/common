package net.lizhaoweb.common.util.http.common;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * 封装Cookie
 *
 * @author arron
 * @version 1.0.0.0.1
 * @notes Created on 2016-1-12 8:42:13<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class HttpCookies {

    /**
     * 使用httpcontext，用于设置和携带Cookie
     */
    private HttpClientContext context;

    /**
     * 储存Cookie
     */
    private CookieStore cookieStore;

    public static HttpCookies custom() {
        return new HttpCookies();
    }

    private HttpCookies() {
        this.context = new HttpClientContext();
        this.cookieStore = new BasicCookieStore();
        this.context.setCookieStore(cookieStore);
    }

    public HttpClientContext getContext() {
        return context;
    }

    public HttpCookies setContext(HttpClientContext context) {
        this.context = context;
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HttpCookies setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

}
