/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:22
 */
package net.lizhaoweb.common.util.compress;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <h1>回调 - 归档实体</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年08月16日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IArchiveEntryCallback<OS extends OutputStream> {

    void addArchiveFile(OS archiveOutputStream, File srcFile, String archiveName) throws IOException;

    void addArchiveDirectory(OS archiveOutputStream, File srcFile, String archiveName) throws IOException;

    void addArchiveEntry(OS archiveOutputStream, File srcFile, String archiveName) throws IOException;
}
