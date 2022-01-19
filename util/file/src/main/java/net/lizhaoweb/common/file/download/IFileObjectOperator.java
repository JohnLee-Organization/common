/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 15:20
 */
package net.lizhaoweb.common.file.download;

import net.lizhaoweb.common.file.GenericRequest;

/**
 * 文件对象操作器
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IFileObjectOperator {

    /**
     * @param genericRequest request
     * @return FileObjectMeta
     */
    FileObjectMetadata getFileObjectMeta(GenericRequest genericRequest);

    /**
     * @param getFileObjectRequest
     * @return
     */
    FileObject getFileObject(GetFileObjectRequest getFileObjectRequest);
}
