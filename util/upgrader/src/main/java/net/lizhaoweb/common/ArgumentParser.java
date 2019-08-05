/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:24
 */
package net.lizhaoweb.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数解析器
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class ArgumentParser {

    protected static Logger logger = new Logger();

    static final String OPTION = "option";
    static final String ARGUMENT = "argument";

    private static Map<Argument, List<String>> OPTIONS = new HashMap<>();
    private static List<String> ARGUMENTS = new ArrayList<>();

    static Map<String, Object> parse(List<String> args) {
        if (args == null) {
            throw new IllegalArgumentException("Arguments is null");
        }
        return parse(args.toArray(new String[0]));
    }

    static Map<String, Object> parse(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Arguments is null");
        }
        logger.fatal("Analysis arguments ...");
        for (String argument : args) {
            logger.fatal("Input argument : %s", argument);
            if (Utils.isBlank(argument)) {
                continue;
            }
            argument = argument.trim();
            int firstIndex = argument.indexOf("=");
            if (firstIndex > 0) {
                String key = argument.substring(0, firstIndex).trim();
                Argument option = Argument.fromName(key);
                if (option == null) {
                    ARGUMENTS.add(argument);
                    continue;
                }
                String value = argument.substring(firstIndex + 1).trim();
                List<String> values = OPTIONS.get(option);
                if (values == null) {
                    values = new ArrayList<>();
                }
                values.add(value);
                OPTIONS.put(option, values);
            } else {
                ARGUMENTS.add(argument);
            }
        }
        logger.debug("Options : %s,   Arguments : %s", OPTIONS, ARGUMENTS);
        logger.fatal("Analysis arguments done.");
        return new HashMap<String, Object>() {
            {
                put(OPTION, OPTIONS);
                put(ARGUMENT, ARGUMENTS);
            }
        };
    }

    static String getStringOption(String option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return null;
        }
        return optionValues.get(0);
    }

    static String getStringOption(Argument option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return null;
        }
        return optionValues.get(0);
    }

    static int getIntOption(String option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return 0;
        }
        return Integer.valueOf(optionValues.get(0));
    }

    static int getIntOption(Argument option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return 0;
        }
        return Integer.valueOf(optionValues.get(0));
    }

    static String[] getOptions(String option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return null;
        }
        return optionValues.toArray(new String[0]);
    }

    static String[] getOptions(Argument option) {
        List<String> optionValues = _getOptions(option);
        if (optionValues == null) {
            return null;
        }
        return optionValues.toArray(new String[0]);
    }


    private static List<String> _getOptions(String option) {
        if (option == null) {
            throw new IllegalArgumentException("The option is null.");
        }
        Argument _option = Argument.fromName(option);
        return _getOptions(_option);
    }


    private static List<String> _getOptions(Argument option) {
        if (option == null) {
            throw new IllegalArgumentException("The option is null.");
        }
        return OPTIONS.get(option);
    }
}

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
enum Argument {
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

    Argument(String name, Class javaType, String describe) {
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
