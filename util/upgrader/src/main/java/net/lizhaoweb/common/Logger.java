/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 13:22
 */
package net.lizhaoweb.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月31日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Logger {

    private static final String NEW_LINE = "\n";
    private Locale locale;
    private PrintStream printStream;
    private SimpleDateFormat simpleDateFormat;
    private int level;

    Logger() {
        this(Locale.CHINESE);
    }

    Logger(Locale locale) {
        this.locale = locale;
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", this.locale);
        String levelName = System.getProperty("cn.lizhaoweb.common.upgrader.log.level");
        if (Utils.isBlank(levelName)) {
            this.level = Level.OFF;
        } else {
            this.level = Level.getLevel(levelName.toUpperCase(this.locale));
        }

        File loggerFile = null;
        String loggerFileName = System.getProperty("cn.lizhaoweb.common.upgrader.log.name");
        if (Utils.isBlank(loggerFileName)) {
            String loggerFilePath = System.getProperty("cn.lizhaoweb.common.upgrader.log.path");
            if (Utils.isBlank(loggerFilePath)) {
                loggerFilePath = System.getProperty("java.io.tmpdir");
            }
            loggerFile = new File(loggerFilePath, "john-lee-upgrader.log");
        } else {
            loggerFile = new File(loggerFileName);
        }
        try {
            boolean appendWrite = true;
            boolean autoFlush = true;
            if (!loggerFile.getParentFile().exists()) {
                loggerFile.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(loggerFile, appendWrite);
            this.printStream = new PrintStream(fileOutputStream, autoFlush, Utils.DEFAULT_CHARSET_STRING);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    void trace(String format, Object... args) {
        if (this.level <= Level.TRACE) {
            this.printStream.printf(locale, this.getMessageFormat("TRACE", format), args);
        }
    }

    void debug(String format, Object... args) {
        if (this.level <= Level.DEBUG) {
            this.printStream.printf(locale, this.getMessageFormat("DEBUG", format), args);
        }
    }

    void info(String format, Object... args) {
        if (this.level <= Level.INFO) {
            this.printStream.printf(locale, this.getMessageFormat("INFO", format), args);
        }
    }

    void warn(String format, Object... args) {
        if (this.level <= Level.WARN) {
            this.printStream.printf(locale, this.getMessageFormat("WARN", format), args);
        }
    }

    void error(String format, Object... args) {
        if (this.level <= Level.ERROR) {
            this.printStream.printf(locale, this.getMessageFormat("ERROR", format), args);
        }
    }

    void error(Throwable e, String format, Object... args) {
        if (this.level <= Level.ERROR) {
            this.printStream.printf(locale, this.getMessageFormat("ERROR", format), args);
        }
        e.printStackTrace(this.printStream);
        this.printStream.flush();
    }

    void fatal(String format, Object... args) {
        if (this.level <= Level.FATAL) {
            this.printStream.printf(locale, this.getMessageFormat("FATAL", format), args);
        }
    }

    private synchronized String getMessageFormat(String levelName, String format) {
        return String.format("[%s] %s %s%s", this.simpleDateFormat.format(new Date()), levelName, format, NEW_LINE);
    }
}

/**
 * 日志级别模型
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class Level {

    static final int OFF = 10000;
    static final int TRACE = 1000;
    static final int DEBUG = 2000;
    static final int INFO = 3000;
    static final int WARN = 4000;
    static final int ERROR = 5000;
    static final int FATAL = 6000;
    private static Map<String, Integer> LEVEL_MAP = new HashMap<>();

    static {
        LEVEL_MAP.put("TRACE", TRACE);
        LEVEL_MAP.put("DEBUG", DEBUG);
        LEVEL_MAP.put("INFO", INFO);
        LEVEL_MAP.put("WARN", WARN);
        LEVEL_MAP.put("ERROR", ERROR);
        LEVEL_MAP.put("FATAL", FATAL);
        LEVEL_MAP.put("OFF", OFF);
    }

    static int getLevel(String levelName) {
        Integer level = LEVEL_MAP.get(levelName);
        return (level == null) ? OFF : level;
    }
}
