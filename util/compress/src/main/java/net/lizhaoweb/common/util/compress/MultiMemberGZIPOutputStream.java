/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:12
 */
package net.lizhaoweb.common.util.compress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class MultiMemberGZIPOutputStream extends GZIPOutputStream {

//    public MultiMemberGZIPOutputStream(OutputStream in, int size) throws IOException {
//        // Wrap the stream in a PushbackOutputStream...
//        super(new PullbackOutputStream(in, size), size);
//        this.size = size;
//    }
//
//    public MultiMemberGZIPOutputStream(OutputStream in) throws IOException {
//        // Wrap the stream in a PushbackOutputStream...
//        super(new PushbackOutputStream(in, 1024));
//        this.size = -1;
//    }

    private MultiMemberGZIPOutputStream(MultiMemberGZIPOutputStream parent) throws IOException {
        super(parent.out);
        this.size = -1;
        this.parent = parent.parent == null ? parent : parent.parent;
        this.parent.child = this;
    }

    private MultiMemberGZIPOutputStream(MultiMemberGZIPOutputStream parent, int size) throws IOException {
        super(parent.out, size);
        this.size = size;
        this.parent = parent.parent == null ? parent : parent.parent;
        this.parent.child = this;
    }

    private MultiMemberGZIPOutputStream parent;
    private MultiMemberGZIPOutputStream child;
    private int size;
    private boolean eos;

    @Override
    public void write(byte[] inputBuffer, int inputBufferOffset, int inputBufferLen) throws IOException {
//        if (eos) {
//            return -1;
//        }
//        if (this.child != null) {
//            return this.child.read(inputBuffer, inputBufferOffset, inputBufferLen);
//        }
//        int charsRead = super.read(inputBuffer, inputBufferOffset, inputBufferLen);
//        if (charsRead == -1) {
//            // Push any remaining buffered data back onto the stream
//            // If the stream is then not empty, use it to construct
//            // a new instance of this class and delegate this and any
//            // future calls to it...
//            int n = inf.getRemaining() - 8;
//            if (n > 0) {
//                // More than 8 bytes remaining in deflater
//                // First 8 are gzip trailer. Add the rest to
//                // any un-read data...
//                ((PushbackOutputStream) this.in).unread(buf, len - n, n);
//            } else {
//                // Nothing in the buffer. We need to know whether or not
//                // there is unread data available in the underlying stream
//                // since the base class will not handle an empty file.
//                // Read a byte to see if there is data and if so,
//                // push it back onto the stream...
//                byte[] b = new byte[1];
//                int ret = out.read(b, 0, 1);
//                if (ret == -1) {
//                    eos = true;
//                    return -1;
//                } else {
//                    ((PushbackOutputStream) this.out).unread(b, 0, 1);
//                }
//            }
//            MultiMemberGZIPOutputStream child;
//            if (this.size == -1) {
//                child = new MultiMemberGZIPOutputStream(this);
//            } else {
//                child = new MultiMemberGZIPOutputStream(this, this.size);
//            }
//            return child.read(inputBuffer, inputBufferOffset, inputBufferLen);
//        } else {
//            return charsRead;
//        }
    }
}
