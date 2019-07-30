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

import sun.management.VMManagement;

import java.awt.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        FileWriter fileWriter = null;
        RandomAccessFile readRandomAccessFile = null, writeRandomAccessFile = null;
        try {
            Map<String, Object> argsMap = ArgumentParser.parse(args);
            String serverStart = ArgumentParser.getStringOption(Argument.CommandForServerStart);
            if (serverStart == null) {
                throw new IllegalComponentStateException("The command for start server is not found. Argument name is '" + Argument.CommandForServerStart.getName() + "'.");
            }
            String serverStop = ArgumentParser.getStringOption(Argument.CommandForServerStop);
            if (serverStop == null) {
                throw new IllegalComponentStateException("The command for stop server is not found. Argument name is '" + Argument.CommandForServerStop.getName() + "'.");
            }
            String serverPid = ArgumentParser.getStringOption(Argument.ProcessIdForServer);
            if (serverPid == null) {
                throw new IllegalComponentStateException("The command for stop server is not found. Argument name is '" + Argument.ProcessIdForServer.getName() + "'.");
            }
            String appFrom = ArgumentParser.getStringOption(Argument.PathForAppFrom);
            if (appFrom == null) {
                throw new IllegalComponentStateException("The path for application-package-from is not found. Argument name is '" + Argument.PathForAppFrom.getName() + "'.");
            }
            File srcFile = new File(appFrom);
            if (!srcFile.exists()) {
                throw new IllegalComponentStateException("The path '" + srcFile.getCanonicalPath() + "' for application-package-from is not exists.");
            }
            if (!srcFile.canRead()) {
                throw new IllegalComponentStateException("The path '" + srcFile.getCanonicalPath() + "' for application-package-from can not read.");
            }
            String appTo = ArgumentParser.getStringOption(Argument.PathForAppTo);
            if (appTo == null) {
                throw new IllegalComponentStateException("The path for application-package-to is not found. Argument name is '" + Argument.PathForAppTo.getName() + "'.");
            }
            File tarFile = new File(appTo);
            if (!tarFile.getParentFile().exists()) {
                throw new IllegalComponentStateException("The directory '" + tarFile.getParentFile().getCanonicalPath() + "' for application-package-to is not exists.");
            }
            if (!tarFile.getParentFile().isDirectory()) {
                throw new IllegalComponentStateException("The directory '" + tarFile.getParentFile().getCanonicalPath() + "' for application-package-to is not a directory.");
            }
            if (!tarFile.getParentFile().canWrite()) {
                throw new IllegalComponentStateException("The path '" + tarFile.getParentFile().getCanonicalPath() + "' for application-package-to can not write.");
            }
            String[] delPathArray = ArgumentParser.getOptions(Argument.PathForDelete);
            int tryCount = 0;

            String tmpFolder = System.getProperty("java.io.tmpdir");
            File runConfigFile = new File(tmpFolder, "john-lee-upgrader-my.pid");
            boolean runConfigFileExists = runConfigFile.exists();
            if (runConfigFileExists) {
                runConfigFile.delete();
            }
            fileWriter = new FileWriter(runConfigFile, true);
            readRandomAccessFile = new RandomAccessFile(runConfigFile, "r");
//            if (runConfigFileExists) {
//                String pid = readRandomAccessFile.readLine();
//                writeRandomAccessFile = new RandomAccessFile(runConfigFile, "rw");
//                writeRandomAccessFile.write((jvmPid() + "").getBytes(), 0, pid.length());
//            } else {
            fileWriter.write(jvmPid() + "\n");
            fileWriter.flush();
//            }

            // 1、停止服务器
//            String _1 = readRandomAccessFile.readLine();
//            if (_1 == null || !"step 1".equals(_1.trim())) {
            System.out.println("Stop the server ...");
            this.execCommand(serverStop);
            fileWriter.write("step 1 \n");
            fileWriter.flush();
//            }

            // 2、等待服务器真正停止
//            String _2 = readRandomAccessFile.readLine();
//            if (_2 == null || !"step 2".equals(_2.trim())) {
            System.out.println("Waiting to stop the server ...");
            List<String> pidList = this.execCommand_2("jps -p");
            tryCount = 0;
            while (pidList.contains(serverPid)) {
                tryCount++;
                System.out.printf("jps -p ==== %d \n", tryCount);
                Thread.sleep(1000L);
            }
            fileWriter.write("step 2\n");
            fileWriter.flush();
//            }

            // 3、删除相关文件或目录
//            String _3 = readRandomAccessFile.readLine();
//            if (_3 == null || !"step 3".equals(_3.trim())) {
            System.out.println("Delete ...");
            tryCount = 0;
            while (tarFile.exists()) {
                tryCount++;
                System.out.printf("Delete [T] '%s' : %s ==== %d \n", tarFile, deleteTree(tarFile), tryCount);
                Thread.sleep(1000L);
            }
            if (delPathArray != null) {
                tryCount = 0;
                for (String delPath : delPathArray) {
                    tryCount++;
                    System.out.printf("Delete [D] '%s'", delPath);
                    File file = new File(delPath);
                    if (!file.exists()) {
                        System.out.println("...");
                        continue;
                    }
                    System.out.printf(" : %s ==== %d \n", deleteTree(file), tryCount);
                }
            }
            fileWriter.write("step 3\n");
            fileWriter.flush();
//            }

            // 4、部署应用
//            String _4 = readRandomAccessFile.readLine();
//            if (_4 == null || !"step 4".equals(_4.trim())) {
            System.out.println("Deploy the application ...");
            copy(srcFile, tarFile);
            fileWriter.write("step 4\n");
            fileWriter.flush();
//            }

            // 5、启动服务器
//            String _5 = readRandomAccessFile.readLine();
//            if (_5 == null || "step 5".equals(_5.trim())) {
            System.out.println("Start the server ...");
            this.execCommand(serverStart);
            fileWriter.write("step 5\n");
            fileWriter.flush();
//            }


//            runConfigFile.delete();

            System.out.println("Done.");
            return 0;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close(readRandomAccessFile);
            close(writeRandomAccessFile);
            close(fileWriter);
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

    public static final int jvmPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            VMManagement mgmt = (VMManagement) jvm.get(runtime);
            Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
            pidMethod.setAccessible(true);
            return (Integer) pidMethod.invoke(mgmt);
        } catch (Throwable e) {
            return -1;
        }
    }

    public static boolean deleteTree(File tree) {
        if (tree == null) {
            throw new IllegalArgumentException("Argument 'fileOrDirectory' is null");
        }
        if (tree.isDirectory()) {
            File[] fileArray = tree.listFiles();
            for (int fileIndex = 0; fileIndex < fileArray.length; fileIndex++) {
                deleteTree(fileArray[fileIndex]);
            }
        }
        return tree.exists() ? tree.delete() : true;
    }
}
