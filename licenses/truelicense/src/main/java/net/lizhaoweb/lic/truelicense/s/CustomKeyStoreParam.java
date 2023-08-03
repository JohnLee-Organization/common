/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 11:59
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.*;

/**
 * 将公私钥存储文件存放到其他磁盘位置而不是项目中
 * <p>
 * Created by Jhon.Lee on 8/3/2023 11:59 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private String storePath;
    private String alias;
    private String storePwd;
    private String keyPwd;

    public CustomKeyStoreParam(Class clazz, String resource, String alias, String storePwd, String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br>
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     *
     * @return java.io.InputStream
     * @throws IOException 流输入输出异常
     */
    @Override
    public InputStream getStream() throws IOException {
        final InputStream in = new FileInputStream(new File(storePath));
        if (null == in) {
            throw new FileNotFoundException(storePath);
        }

        return in;
    }

}
