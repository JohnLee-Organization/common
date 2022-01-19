/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file.event
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 12:10
 */
package net.lizhaoweb.common.file.event;

import lombok.Getter;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class ProgressInputStream extends FilterInputStream {

    private static final int DEFAULT_NOTIFICATION_THRESHOLD = 8 * 1024;

    @Getter
    private final ProgressListener listener;
    private final int notifyThresHold;
    @Getter
    private int unnotifiedByteCount;
    private boolean hasBeenRead;
    private boolean doneEOF;
    @Getter
    private long notifiedByteCount;

    public ProgressInputStream(InputStream is, ProgressListener listener) {
        this(is, listener, DEFAULT_NOTIFICATION_THRESHOLD);
    }

    public ProgressInputStream(InputStream is, ProgressListener listener, int notifyThresHold) {
        super(is);
        if (is == null || listener == null) {
            throw new IllegalArgumentException();
        }
        this.listener = listener;
        this.notifyThresHold = notifyThresHold;
    }

    @Override
    public int read() throws IOException {
        if (!hasBeenRead) {
            onFirstRead();
            hasBeenRead = true;
        }
        int ch = super.read();
        if (ch == -1) {
            eof();
        } else {
            onBytesRead(1);
        }
        return ch;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        onReset();
        unnotifiedByteCount = 0;
        notifiedByteCount = 0;
    }

    @Override
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (!hasBeenRead) {
            onFirstRead();
            hasBeenRead = true;
        }
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            eof();
        } else {
            onBytesRead(bytesRead);
        }
        return bytesRead;
    }

    public final InputStream getWrappedInputStream() {
        return in;
    }

    @Override
    public void close() throws IOException {
        onClose();
        super.close();
    }

    protected void onFirstRead() {
    }

    protected void onEOF() {
    }

    protected void onClose() {
        eof();
    }

    protected void onReset() {
    }

    protected void onNotifyBytesRead() {
    }

    private void onBytesRead(int bytesRead) {
        unnotifiedByteCount += bytesRead;
        if (unnotifiedByteCount >= notifyThresHold) {
            onNotifyBytesRead();
            notifiedByteCount += unnotifiedByteCount;
            unnotifiedByteCount = 0;
        }
    }

    private void eof() {
        if (doneEOF)
            return;
        onEOF();
        unnotifiedByteCount = 0;
        doneEOF = true;
    }
}
