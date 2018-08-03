package net.lizhaoweb.common.util.base;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <h3>Cookie工具类</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public final class CookieUtil {

    private CookieUtil() {
        super();
    }

    /**
     * 增加一个COOKIE。
     *
     * @param response 响应对象。
     * @param name     COOKIE名。
     * @param value    COOKIE值。
     */
    public static final void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);// name为从登录界面取得的用户名，把它加入到cookie里面
        // cookie.setComment(purpose);
        // cookie.setHttpOnly(httpOnly);
        // cookie.setSecure(flag);
        // cookie.setVersion(v);
        // cookie.setDomain(pattern);
        cookie.setPath("/");
        cookie.setMaxAge(-1);// 设置cookie的生命周期为：会话级，即浏览器关闭，该cookie就消失了
        response.addCookie(cookie);
    }

    /**
     * 增加一批COOKIE。
     *
     * @param response 响应对象。
     * @param cookies  MAP对象。
     */
    public static final void addCookies(HttpServletResponse response, Map<String, String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            Set<String> keySet = cookies.keySet();
            Iterator<String> keyIterator = keySet.iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                addCookie(response, key, cookies.get(key));
            }
        }
    }

    /**
     * 移除COOKIE.
     *
     * @param request 请求对象。
     * @param name    COOKIE名。
     */
    public static final void removeCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName != null && cookieName.equalsIgnoreCase(name)) {
                    cookie.setMaxAge(0);
                }
            }
        }
    }

    /**
     * 获得cookie的每页条数
     * <p>
     * 使用_cookie_page_size作为cookie name
     *
     * @param request HttpServletRequest
     * @return default:20 max:200
     */
    public static int getPageSize(HttpServletRequest request) {
        Validate.notNull(request);
        Cookie cookie = getCookie(request, Constant.Cookie.Page.Size.KEY);
        int count = 0;
        if (cookie != null) {
            if (NumberUtils.isDigits(cookie.getValue())) {
                count = Integer.parseInt(cookie.getValue());
            }
        }
        if (count <= 0) {
            count = Constant.Cookie.Page.Size.DEFAULT;
        } else if (count > Constant.Cookie.Page.Size.MAX) {
            count = Constant.Cookie.Page.Size.MAX;
        }
        return count;
    }

    /**
     * 获得cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return if exist return cookie, else return null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Validate.notNull(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 根据部署路径，将cookie保存在根目录。
     *
     * @param request  请求对象。
     * @param response 响应对象。
     * @param name     Cookie名。
     * @param value    Cookie值。
     * @param expiry   失效时间。
     * @param domain   储存域。
     * @return 返回Cookie对象
     */
    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain) {
        return addCookie(request, response, name, value, expiry, domain, null);
    }

    /**
     * 根据部署路径，将cookie保存在指定目录。
     *
     * @param request  请求对象。
     * @param response 响应对象。
     * @param name     Cookie名。
     * @param value    Cookie值。
     * @param expiry   失效时间。
     * @param domain   储存域。
     * @param path     指定COOKIE路径。
     * @return 返回Cookie对象
     */
    public static Cookie addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry, String domain, String path) {
        Cookie cookie = new Cookie(name, value);
        if (expiry != null) {
            cookie.setMaxAge(expiry);
        }
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isBlank(path)) {
            String ctx = request.getContextPath();
            path = StringUtils.isBlank(ctx) ? "/" : ctx;
        }
        cookie.setPath(path);
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * 取消cookie
     *
     * @param request  请求对象。
     * @param response 响应对象。
     * @param name     Cookie名。
     * @param domain   储存域。
     */
    public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        cancleCookie(request, response, name, domain, null);
    }

    /**
     * 取消cookie
     *
     * @param request  请求对象。
     * @param response 响应对象。
     * @param name     Cookie名。
     * @param domain   储存域。
     * @param path     指定COOKIE路径。
     */
    public static void cancleCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain, String path) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isBlank(path)) {
            String ctx = request.getContextPath();
            path = StringUtils.isBlank(ctx) ? "/" : ctx;
        }
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}
