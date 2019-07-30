/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:22
 */
package net.lizhaoweb.common;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 升级器
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Upgrader {

    public static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * 程序升级器
     *
     * @param args 命令。
     *             serverStart -- 服务器启动命令
     *             serverStop  -- 服务器停止命令
     *             serverPid   -- 服务器进程号
     *             appFrom     -- 服务程序保存位置
     *             appTo       -- 服务程序部署位置
     */
    public static void main(String[] args) {
        Upgrader upgrader = new Upgrader();
        upgrader.deploy(args);
    }

    public int deploy(String[] args) {
        try {
            Map<String, Object> argsMap = ArgumentParser.parse(args);
            String serverStart = ArgumentParser.getStringOption(Argument.CommandForServerStart);
            if (serverStart == null) {
                throw new IllegalComponentStateException("The command for start server is not found.");
            }
            String serverStop = ArgumentParser.getStringOption(Argument.CommandForServerStop);
            if (serverStop == null) {
                throw new IllegalComponentStateException("The command for stop server is not found.");
            }
            String serverPid = ArgumentParser.getStringOption(Argument.ProcessIdForServer);
            if (serverPid == null) {
                throw new IllegalComponentStateException("The command for stop server is not found.");
            }
            String appFrom = ArgumentParser.getStringOption(Argument.PathForAppFrom);
            if (appFrom == null) {
                throw new IllegalComponentStateException("The path for application-package-from is not found.");
            }
            File srcFile = new File(appFrom);
            if (!srcFile.exists()) {
                throw new IllegalComponentStateException("The path for application-package-from is not exists.");
            }
            if (!srcFile.canRead()) {
                throw new IllegalComponentStateException("The path for application-package-from can not read.");
            }
            String appTo = ArgumentParser.getStringOption(Argument.PathForAppTo);
            if (appTo == null) {
                throw new IllegalComponentStateException("The path for application-package-to is not found.");
            }
            File tarFile = new File(appTo);
            if (!tarFile.getParentFile().exists()) {
                throw new IllegalComponentStateException("The directory for application-package-to is not exists.");
            }
            if (!tarFile.getParentFile().isDirectory()) {
                throw new IllegalComponentStateException("The directory for application-package-to is not a directory.");
            }
            if (!tarFile.getParentFile().canWrite()) {
                throw new IllegalComponentStateException("The path for application-package-to can not write.");
            }

            // 1、停止服务器
            this.execCommand(serverStop);

            // 2、等待服务器真正停止
            List<String> pidList = this.execCommand_2("jps -p");
            while (pidList.contains(serverPid)) {
                Thread.sleep(1000L);
            }

            // 3、查看目标文件是否存在
            while (tarFile.exists()) {
                tarFile.delete();
                Thread.sleep(1000L);
            }

            // 4、部署应用
            copy(srcFile, tarFile);

            // 5、启动服务器
            this.execCommand(serverStart);

            return 0;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void execCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    private java.util.List<String> execCommand_2(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        return readLines(process.getInputStream(), "UTF-8");
    }

    public static boolean copy(File srcFile, File targetFile) {
        boolean success = false;
        if (srcFile.exists()) { //文件存在时
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(srcFile); //读入原文件
                outputStream = new FileOutputStream(targetFile);
                long copied = copyLarge(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE]);

                success = copied > -1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                close(outputStream);
                close(inputStream);
            }
        }
        return success;
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static final void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
                closeable = null;
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static List<String> readLines(final InputStream input, final String encoding) throws IOException {
        return readLines(input, Charset.forName(encoding));
    }

    public static List<String> readLines(final InputStream input, final Charset encoding) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, encoding);
        return readLines(reader);
    }

    public static List<String> readLines(final Reader input) throws IOException {
        final BufferedReader reader = toBufferedReader(input);
        final List<String> list = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }
}
