/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:37
 */
package org.hyperic.sigar;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年02月20日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ResourceLimit implements Serializable {
    private static final long serialVersionUID = 32184L;

    @Getter
    long cpuCur = 0L;

    @Getter
    long cpuMax = 0L;

    @Getter
    long fileSizeCur = 0L;

    @Getter
    long fileSizeMax = 0L;

    @Getter
    long pipeSizeMax = 0L;

    @Getter
    long pipeSizeCur = 0L;

    @Getter
    long dataCur = 0L;

    @Getter
    long dataMax = 0L;

    @Getter
    long stackCur = 0L;

    @Getter
    long stackMax = 0L;

    @Getter
    long coreCur = 0L;

    @Getter
    long coreMax = 0L;

    @Getter
    long memoryCur = 0L;

    @Getter
    long memoryMax = 0L;

    @Getter
    long processesCur = 0L;

    @Getter
    long processesMax = 0L;

    @Getter
    long openFilesCur = 0L;

    @Getter
    long openFilesMax = 0L;

    @Getter
    long virtualMemoryCur = 0L;

    @Getter
    long virtualMemoryMax = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static ResourceLimit fetch(Sigar sigar) throws SigarException {
        ResourceLimit resourceLimit = new ResourceLimit();
        resourceLimit.gather(sigar);
        return resourceLimit;
    }

    void copyTo(ResourceLimit copy) {
        copy.cpuCur = this.cpuCur;
        copy.cpuMax = this.cpuMax;
        copy.fileSizeCur = this.fileSizeCur;
        copy.fileSizeMax = this.fileSizeMax;
        copy.pipeSizeMax = this.pipeSizeMax;
        copy.pipeSizeCur = this.pipeSizeCur;
        copy.dataCur = this.dataCur;
        copy.dataMax = this.dataMax;
        copy.stackCur = this.stackCur;
        copy.stackMax = this.stackMax;
        copy.coreCur = this.coreCur;
        copy.coreMax = this.coreMax;
        copy.memoryCur = this.memoryCur;
        copy.memoryMax = this.memoryMax;
        copy.processesCur = this.processesCur;
        copy.processesMax = this.processesMax;
        copy.openFilesCur = this.openFilesCur;
        copy.openFilesMax = this.openFilesMax;
        copy.virtualMemoryCur = this.virtualMemoryCur;
        copy.virtualMemoryMax = this.virtualMemoryMax;
    }

    public static native long INFINITY();

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strcpuCur = String.valueOf(this.cpuCur);
        if (!"-1".equals(strcpuCur)) {
            map.put("CpuCur", strcpuCur);
        }

        String strcpuMax = String.valueOf(this.cpuMax);
        if (!"-1".equals(strcpuMax)) {
            map.put("CpuMax", strcpuMax);
        }

        String strfileSizeCur = String.valueOf(this.fileSizeCur);
        if (!"-1".equals(strfileSizeCur)) {
            map.put("FileSizeCur", strfileSizeCur);
        }

        String strfileSizeMax = String.valueOf(this.fileSizeMax);
        if (!"-1".equals(strfileSizeMax)) {
            map.put("FileSizeMax", strfileSizeMax);
        }

        String strpipeSizeMax = String.valueOf(this.pipeSizeMax);
        if (!"-1".equals(strpipeSizeMax)) {
            map.put("PipeSizeMax", strpipeSizeMax);
        }

        String strpipeSizeCur = String.valueOf(this.pipeSizeCur);
        if (!"-1".equals(strpipeSizeCur)) {
            map.put("PipeSizeCur", strpipeSizeCur);
        }

        String strdataCur = String.valueOf(this.dataCur);
        if (!"-1".equals(strdataCur)) {
            map.put("DataCur", strdataCur);
        }

        String strdataMax = String.valueOf(this.dataMax);
        if (!"-1".equals(strdataMax)) {
            map.put("DataMax", strdataMax);
        }

        String strstackCur = String.valueOf(this.stackCur);
        if (!"-1".equals(strstackCur)) {
            map.put("StackCur", strstackCur);
        }

        String strstackMax = String.valueOf(this.stackMax);
        if (!"-1".equals(strstackMax)) {
            map.put("StackMax", strstackMax);
        }

        String strcoreCur = String.valueOf(this.coreCur);
        if (!"-1".equals(strcoreCur)) {
            map.put("CoreCur", strcoreCur);
        }

        String strcoreMax = String.valueOf(this.coreMax);
        if (!"-1".equals(strcoreMax)) {
            map.put("CoreMax", strcoreMax);
        }

        String strmemoryCur = String.valueOf(this.memoryCur);
        if (!"-1".equals(strmemoryCur)) {
            map.put("MemoryCur", strmemoryCur);
        }

        String strmemoryMax = String.valueOf(this.memoryMax);
        if (!"-1".equals(strmemoryMax)) {
            map.put("MemoryMax", strmemoryMax);
        }

        String strprocessesCur = String.valueOf(this.processesCur);
        if (!"-1".equals(strprocessesCur)) {
            map.put("ProcessesCur", strprocessesCur);
        }

        String strprocessesMax = String.valueOf(this.processesMax);
        if (!"-1".equals(strprocessesMax)) {
            map.put("ProcessesMax", strprocessesMax);
        }

        String stropenFilesCur = String.valueOf(this.openFilesCur);
        if (!"-1".equals(stropenFilesCur)) {
            map.put("OpenFilesCur", stropenFilesCur);
        }

        String stropenFilesMax = String.valueOf(this.openFilesMax);
        if (!"-1".equals(stropenFilesMax)) {
            map.put("OpenFilesMax", stropenFilesMax);
        }

        String strvirtualMemoryCur = String.valueOf(this.virtualMemoryCur);
        if (!"-1".equals(strvirtualMemoryCur)) {
            map.put("VirtualMemoryCur", strvirtualMemoryCur);
        }

        String strvirtualMemoryMax = String.valueOf(this.virtualMemoryMax);
        if (!"-1".equals(strvirtualMemoryMax)) {
            map.put("VirtualMemoryMax", strvirtualMemoryMax);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
