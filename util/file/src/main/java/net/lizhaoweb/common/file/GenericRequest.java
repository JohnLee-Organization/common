/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 10:37
 */
package net.lizhaoweb.common.file;

import lombok.Getter;
import lombok.Setter;
import net.lizhaoweb.common.file.event.ProgressListener;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GenericRequest {

    @Setter
    @Getter
    private ProgressListener progressListener = ProgressListener.NOOP;

    @Setter
    @Getter
    private String uri;

    @Setter
    @Getter
    private String queryString;

    public GenericRequest() {
    }

    public GenericRequest(String uri) {
        this(uri, null);
    }

    public GenericRequest(String uri, String queryString) {
        this.uri = uri;
        this.queryString = queryString;
    }

    public GenericRequest withUri(String uri) {
        this.setUri(uri);
        return this;
    }

    public GenericRequest withQueryString(String queryString) {
        this.setQueryString(queryString);
        return this;
    }
}
