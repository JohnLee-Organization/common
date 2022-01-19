/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 14:55
 */
package net.lizhaoweb.common.file.download;

import net.lizhaoweb.common.file.GenericRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class ObjectStat implements Serializable {

    public long size; // file size
    public Date lastModified; // file's last modified time.

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
        result = prime * result + (int) (size ^ (size >>> 32));
        return result;
    }

    public static ObjectStat getFileStat(IFileObjectOperator operator, String uri, String queryString) {
        GenericRequest genericRequest = new GenericRequest(uri, queryString);
        FileObjectMetadata meta = operator.getFileObjectMeta(genericRequest);

        ObjectStat objStat = new ObjectStat();
        objStat.size = meta.getSize();
        objStat.lastModified = meta.getLastModified();

        return objStat;
    }
}
