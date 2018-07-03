/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.os.linux
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:52
 */
package net.lizhaoweb.common.util.os.command.impl.linux;

import net.lizhaoweb.common.util.os.command.impl.CommandExecutor;
import net.lizhaoweb.common.util.os.model.Partition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>执行器 - Linux 操作系统命令</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年07月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CommandExecutorForLinux extends CommandExecutor {

    protected static Logger logger = LoggerFactory.getLogger(CommandExecutorForLinux.class);

    private static CommandExecutorForLinux commandExecutorInstance;

    /**
     * @return
     */
    public static CommandExecutorForLinux getInstance() {
        synchronized (commandExecutorInstance) {
            if (commandExecutorInstance == null) {
                commandExecutorInstance = new CommandExecutorForLinux();
            }
            return commandExecutorInstance;
        }
    }

    /**
     * 获取 Linux 分区信息
     *
     * @param partition 分区目录
     * @return List<Partition>
     */
    public static List<Partition> partitionInfo(String partition) {
        Calendar.getInstance();
        List<Partition> partitionList = new ArrayList<Partition>();
        try {
            CommandExecutor commandExecuter = getInstance();
            String command = String.format("df -k %s", partition);
            commandExecuter.execute(command);
            String partitionInfo = commandExecuter.inputContent();
            logger.info("Partitions information {}", partitionInfo);

            Matcher matcher = Pattern.compile("(.*?)\\n?\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+(?:\\.\\d+)?)%\\s+(.*?)\\n").matcher(partitionInfo);
            while (matcher.find()) {
                String filesystem = matcher.group(1);
                Long size;
                try {
                    size = Long.valueOf(matcher.group(2));
                } catch (Exception e) {
                    size = -1L;
                }
                Long used;
                try {
                    used = Long.valueOf(matcher.group(3));
                } catch (Exception e) {
                    used = -1L;
                }
                Long available;
                try {
                    available = Long.valueOf(matcher.group(4));
                } catch (Exception e) {
                    available = -1L;
                }
                double usePercentage;
                try {
                    usePercentage = Double.valueOf(matcher.group(5));
                } catch (Exception e) {
                    usePercentage = -1L;
                }
                String mountedOn = matcher.group(6);
                Partition partitionBean = new Partition(filesystem, size, used, available, usePercentage, mountedOn);
                logger.info("[Partition]{}", partitionBean);
                partitionList.add(partitionBean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return partitionList;
    }
}
