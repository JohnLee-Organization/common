/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:54
 */
package net.lizhaoweb.common;

/**
 * 参数模型
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public enum Argument {
    CommandForServerStart("serverStart", String.class, "服务器启动命令"),
    CommandForServerStop("serverStop", String.class, "服务器停止命令"),
    ProcessIdForServer("serverPid", String.class, "服务器进程号"),
    //    PortForServer("serverPort", String.class, "服务器端口号"),
    PathForAppFrom("appFrom", String.class, "服务程序保存位置"),
    PathForAppTo("appTo", String.class, "服务程序部署位置"),
    PathForDelete("delPath", String.class, "要删除的目录");

    private String name;
    private String describe;
    private Class javaType;

    private Argument(String name, Class javaType, String describe) {
        this.name = name;
        this.javaType = javaType;
        this.describe = describe;
    }

    public String getName() {
        return this.name;
    }

    public String getDescribe() {
        return this.describe;
    }

    public Class getJavaType() {
        return this.javaType;
    }

    public static Argument fromName(String name) {
        for (Argument argument : values()) {
            if (argument.name.equals(name)) {
                return argument;
            }
        }
        return null;
    }
}
