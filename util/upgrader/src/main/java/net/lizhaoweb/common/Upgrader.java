/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 10:22
 */
package net.lizhaoweb.common;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    private static Logger logger = new Logger();

    public Upgrader() {
        super();
    }

    private int deploy(String[] args) {
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

            // 1、停止服务器
            logger.info("Stop the server ...");
            this.execCommand(serverStop);

            // 2、等待服务器真正停止
            logger.info("Waiting to stop the server ...");
            List<String> pidList = this.execCommand_2("jps -p");
            tryCount = 0;
            while (pidList.contains(serverPid)) {
                tryCount++;
                logger.debug("jps -p ==== %d", tryCount);
                Thread.sleep(1000L);
            }

            // 3、删除相关文件或目录
            logger.info("Delete ...");
            tryCount = 0;
            while (tarFile.exists()) {
                tryCount++;
                logger.debug("Delete [Target] '%s' : %s ==== %d", tarFile, Utils.deleteTree(tarFile), tryCount);
                Thread.sleep(1000L);
            }
            if (delPathArray != null) {
                tryCount = 0;
                for (String delPath : delPathArray) {
                    tryCount++;
                    logger.debug("Delete [Appoint] '%s'", delPath);
                    File file = new File(delPath);
                    if (!file.exists()) {
                        logger.info("...");
                        continue;
                    }
                    logger.debug("Delete [Appoint] '%s' : %s ==== %d", delPath, Utils.deleteTree(file), tryCount);
                }
            }

            // 4、部署应用
            logger.info("Deploy the application ...");
            Utils.copyFile(srcFile, tarFile);

            // 5、启动服务器
            logger.info("Start the server ...");
            this.execCommand(serverStart);

            return 0;
        } catch (Throwable e) {
            logger.error(e, e.getMessage());
        } finally {
            logger.fatal("Deploy done.");
        }
        return -1;
    }

    private void execCommand(String command) throws IOException, InterruptedException {
        logger.debug("Execute command : %s", command);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    private java.util.List<String> execCommand_2(String command) throws IOException, InterruptedException {
        logger.debug("Execute command : %s", command);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        return Utils.readLines(process.getInputStream(), Utils.DEFAULT_CHARSET);
    }

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
        logger.fatal("Upgrade service ...");
        Upgrader upgrader = new Upgrader();
        int tryCount = 0, maxRetry = 5, upgrade;
        while ((upgrade = upgrader.deploy(args)) != 0) {
            logger.error("Try upgrade server for %s times.", ++tryCount);
            if (tryCount >= maxRetry) {
                break;
            }
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                logger.error(e, e.getMessage());
            }
        }
        if (upgrade == 0) {
            logger.fatal("Upgrade service success.\n");
        } else {
            logger.fatal("Upgrade service fail.\n");
        }
    }
}
