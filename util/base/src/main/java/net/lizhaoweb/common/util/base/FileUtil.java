package net.lizhaoweb.common.util.base;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * <h3>File工具类</h3>
 * <p>
 * 这个类用于操作 File 对象。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public final class FileUtil extends FileUtils {

    /**
     * The file move buffer size (30 MB)
     */
    private static final long FILE_MOVE_BUFFER_SIZE = ONE_MB * 30;

    private FileUtil() {
        super();
    }

    /**
     * 格式化文件大小
     *
     * @param fileSize 文件大小(LONG)
     * @param newScale 精度
     * @return 返回字符串的文件大小。
     */
    public static final String formateSize(long fileSize, Integer newScale) {
        String formatFileSize = null;
        if (fileSize > -1 && fileSize <= Long.MAX_VALUE) {
            formatFileSize = formateSize((double) fileSize, newScale);
        } else {
            formatFileSize = null;
        }
        return formatFileSize;
    }

    /**
     * 格式化文件大小
     *
     * @param fileSize 文件大小 (DOUBLE)
     * @param newScale 精度
     * @return 返回字符串的文件大小。
     */
    public static final String formateSize(double fileSize, Integer newScale) {
        String formatFileSize = null;
        if (fileSize > -1 && fileSize <= Double.MAX_VALUE) {
            BigDecimal size = null;
            String unit = null;
            BigDecimal fileSizeB = new BigDecimal(fileSize);
            if (fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1125899906842624l)).doubleValue() >= 1) {
                size = fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1125899906842624l));
                unit = "BB";
            } else if (fileSizeB.divide(new BigDecimal(1125899906842624l)).divide(new BigDecimal(1073741824)).doubleValue() >= 1) {
                size = fileSizeB.divide(new BigDecimal(1125899906842624l)).divide(new BigDecimal(1073741824));
                unit = "YB";
            } else if (fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1073741824)).doubleValue() >= 1) {
                size = fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1073741824));
                unit = "ZB";
            } else if (fileSizeB.divide(new BigDecimal(1073741824)).divide(new BigDecimal(1073741824)).doubleValue() >= 1) {
                size = fileSizeB.divide(new BigDecimal(1073741824)).divide(new BigDecimal(1073741824));
                unit = "EB";
            } else if (fileSizeB.divide(new BigDecimal(1125899906842624l)).doubleValue() >= 1) {// 1024^5=1125899906842624
                size = fileSizeB.divide(new BigDecimal(1125899906842624l));
                unit = "PB";
            } else if (fileSizeB.divide(new BigDecimal(1099511627776l)).doubleValue() >= 1) {// 1024^4=1099511627776
                size = fileSizeB.divide(new BigDecimal(1099511627776l));
                unit = "TB";
            } else if (fileSizeB.divide(new BigDecimal(1073741824)).doubleValue() >= 1) {// 1024^3=1073741824
                size = fileSizeB.divide(new BigDecimal(1073741824));
                unit = "GB";
            } else if (fileSizeB.divide(new BigDecimal(1048576)).doubleValue() >= 1) {// 1024^2=1048576
                size = fileSizeB.divide(new BigDecimal(1048576));
                unit = "MB";
            } else if (fileSizeB.divide(new BigDecimal(1024)).doubleValue() >= 1) {
                size = fileSizeB.divide(new BigDecimal(1024));
                unit = "KB";
            } else {
                size = fileSizeB;
                unit = "B";
            }
            if (newScale != null) {
                size = size.setScale(newScale, BigDecimal.ROUND_HALF_UP);
            }
            formatFileSize = size.toString() + unit;
        } else {
            formatFileSize = null;
        }
        return formatFileSize;
    }

    /**
     * 指定单位格式化文件大小
     *
     * @param unit     指定单位
     * @param fileSize 文件大小(DOUBLE)
     * @param newScale 精度
     * @return 返回字符串的文件大小。
     */
    public static final String formateSizeByUnit(String unit, double fileSize, Integer newScale) {
        String formatFileSize = null;
        if (fileSize > -1 && fileSize <= Double.MAX_VALUE) {
            BigDecimal size = null;
            BigDecimal fileSizeB = new BigDecimal(fileSize);
            if ("BB".equalsIgnoreCase(unit)) {
                size = fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1125899906842624l));
                unit = "BB";
            } else if ("YB".equalsIgnoreCase(unit)) {
                size = fileSizeB.divide(new BigDecimal(1125899906842624l)).divide(new BigDecimal(1073741824));
                unit = "YB";
            } else if ("ZB".equalsIgnoreCase(unit)) {
                size = fileSizeB.divide(new BigDecimal(1099511627776l)).divide(new BigDecimal(1073741824));
                unit = "ZB";
            } else if ("EB".equalsIgnoreCase(unit)) {
                size = fileSizeB.divide(new BigDecimal(1073741824)).divide(new BigDecimal(1073741824));
                unit = "EB";
            } else if ("PB".equalsIgnoreCase(unit)) {// 1024^5=1125899906842624
                size = fileSizeB.divide(new BigDecimal(1125899906842624l));
                unit = "PB";
            } else if ("TB".equalsIgnoreCase(unit)) {// 1024^4=1099511627776
                size = fileSizeB.divide(new BigDecimal(1099511627776l));
                unit = "TB";
            } else if ("GB".equalsIgnoreCase(unit)) {// 1024^3=1073741824
                size = fileSizeB.divide(new BigDecimal(1073741824));
                unit = "GB";
            } else if ("MB".equalsIgnoreCase(unit)) {// 1024^2=1048576
                size = fileSizeB.divide(new BigDecimal(1048576));
                unit = "MB";
            } else if ("KB".equalsIgnoreCase(unit)) {
                size = fileSizeB.divide(new BigDecimal(1024));
                unit = "KB";
            } else {
                size = fileSizeB;
                unit = "B";
            }
            if (newScale != null) {
                size = size.setScale(newScale, BigDecimal.ROUND_HALF_UP);
            }
            formatFileSize = size.toString() + unit;
        } else {
            formatFileSize = null;
        }
        return formatFileSize;
    }

    /**
     * 指定单位格式化文件大小
     *
     * @param unit     指定单位
     * @param fileSize 文件大小(STRING)
     * @param newScale 精度
     * @return 返回字符串的文件大小。
     */
    public static final String formateSizeByUnit(String unit, String fileSize, Integer newScale) {
        double size = formateSizeToDouble(fileSize);
        return formateSizeByUnit(unit, size, newScale);
    }

    /**
     * 将字符串文件大小转换为长整形数字
     *
     * @param fileSize 字符串文件大小
     * @return 返回文件的字节大小。
     */
    public static final Long formateSizeToLong(String fileSize) {
        if (StringUtil.isBlank(fileSize)) {
            return null;
        }
        Long byteSize = null;
        String fileSizeString = fileSize.trim().toUpperCase();
        if (Pattern.compile("(KB)|(MB)|(GB)|(TB)|(PB)$", Pattern.CASE_INSENSITIVE).matcher(fileSizeString).find()) {
            byteSize = formateSizeToDouble(fileSizeString).longValue();
        } else if (fileSizeString.endsWith("EB") && new BigDecimal(fileSizeString.replace("EB", "").trim()).doubleValue() < 8) {
            byteSize = formateSizeToDouble(fileSizeString).longValue();
        } else if (Pattern.compile("\\dB$", Pattern.CASE_INSENSITIVE).matcher(fileSizeString).find()) {
            byteSize = formateSizeToDouble(fileSizeString).longValue();
        }
        return byteSize;
    }

    /**
     * 将字符串文件大小转换为双精度浮点数字
     *
     * @param fileSize 字符串文件大小
     * @return 返回文件的字节大小。
     */
    public static final Double formateSizeToDouble(String fileSize) {
        if (StringUtil.isBlank(fileSize)) {
            return null;
        }
        BigDecimal byteSize = null;
        String fileSizeString = fileSize.trim().toUpperCase();
        if (fileSizeString.endsWith("BB")) {
            String sizeString = fileSizeString.replace("BB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1099511627776l)).multiply(new BigDecimal(1125899906842624l));
        } else if (fileSizeString.endsWith("YB")) {
            String sizeString = fileSizeString.replace("YB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1125899906842624l)).multiply(new BigDecimal(1073741824));
        } else if (fileSizeString.endsWith("ZB")) {
            String sizeString = fileSizeString.replace("ZB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1099511627776l)).multiply(new BigDecimal(1073741824));
        } else if (fileSizeString.endsWith("EB")) {
            String sizeString = fileSizeString.replace("EB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1073741824)).multiply(new BigDecimal(1073741824));
        } else if (fileSizeString.endsWith("PB")) {
            String sizeString = fileSizeString.replace("PB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1125899906842624l));
        } else if (fileSizeString.endsWith("TB")) {
            String sizeString = fileSizeString.replace("TB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1099511627776l));
        } else if (fileSizeString.endsWith("GB")) {
            String sizeString = fileSizeString.replace("GB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1073741824));
        } else if (fileSizeString.endsWith("MB")) {
            String sizeString = fileSizeString.replace("MB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1048576));
        } else if (fileSizeString.endsWith("KB")) {
            String sizeString = fileSizeString.replace("KB", "").trim();
            byteSize = new BigDecimal(sizeString).multiply(new BigDecimal(1024));
        } else if (fileSizeString.endsWith("B")) {
            String sizeString = fileSizeString.replace("B", "").trim();
            byteSize = new BigDecimal(sizeString);

        }
        return byteSize.doubleValue();
    }

    /**
     * 格式化文件路径
     *
     * @param paths 文件路径 。
     * @return 返回格式化的路径。
     */
    public static final String format(String... paths) {
        String result = "";
        for (String path : paths) {
            result = String.format("%s%s%s", result, path, File.separator);
        }
        if ("\\".equals(File.separator)) {
            while (result.contains("/")) {
                result = result.replace("/", "\\");
            }
            while (result.contains("\\\\")) {
                result = result.replace("\\\\", "\\");
            }
        } else {
            while (result.contains("\\")) {
                result = result.replace("\\", "/");
            }
            while (result.contains("//")) {
                result = result.replace("//", "/");
            }
        }
        return result;
    }

    /**
     * 删除文件或目录。
     *
     * @param file 文件或目录。
     * @return 返回是否删除成功。
     */
    public static final boolean remove(File file) {
        boolean result = false;
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 删除文件或目录。
     *
     * @param fileName 文件名或目录名--包含路径。
     * @return 返回是否删除成功。
     */
    public static final boolean remove(String fileName) {
        File file = new File(fileName);
        return remove(file);
    }

    /**
     * 删除文件或目录。
     *
     * @param fileName 文件名或目录名--包含路径。
     * @param dir      true为目录，false为文件。
     * @return 返回是否删除成功。
     */
    public static final boolean remove(String fileName, boolean dir) {
        boolean result = false;
        File file = new File(fileName);
        if (dir && file.isDirectory()) {// 目录
            result = remove(file);
        } else if (!dir && file.isFile()) {// 文件
            result = remove(file);
        }
        return result;
    }

    /**
     * 通过正则表达式解析允许的文件类型。
     *
     * @param allowedTypesReg 允许的文件类型正则表达式。
     * @return 返回允许的文件类型。
     */
    public static final String getAllowedTypes(String allowedTypesReg) {
        StringBuffer result = new StringBuffer();
        if (!StringUtil.isBlank(allowedTypesReg)) {
            List<String> list = new ArrayList<String>();
            StringTokenizer stringTokenizer = new StringTokenizer(allowedTypesReg, "(|)");
            while (stringTokenizer.hasMoreElements()) {
                String token = (String) stringTokenizer.nextElement();
                if (token.indexOf("/") >= token.length() - 1) {
                    continue;
                }
                int index = token.indexOf("/");
                if (index > -1) {
                    token = token.substring(index + 1);
                }
                list.add(token);
            }
            result.append(CollectionUtil.ListToString(list));
        }
        return result.toString();
    }

    /**
     * 获取文件名
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @return 返回文件完整名称。
     */
    public static final String getFileName(String filePath, String fileName) {
        String result = null;
        if (!StringUtil.isBlank(filePath) && !StringUtil.isBlank(fileName)) {
            // result = new File(filePath, fileName).getPath();
            result = format(filePath, "/", fileName);
        }
        return result;
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件名称
     * @return boolean
     */
    public static final boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 创建文件。如果文件不存在，则创建一个新文件；否则不创建。
     *
     * @param fileName 文件名
     * @return boolean
     * @throws IOException 输入输出异常
     */
    public static final boolean createFile(String fileName) throws IOException {
        if (StringUtil.isBlank(fileName)) {
            return false;
        }
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            return true;
        }
        return file.createNewFile();
    }

    /**
     * 创建目录。如果目录不存在，则创建目录；否则不创建。如果存在同名文件则先删除文件后创建目录.
     *
     * @param directoryName 目录名
     * @return boolean
     */
    public static final boolean createDirectory(String directoryName) {
        if (StringUtil.isBlank(directoryName)) {
            return false;
        }
        File dir = new File(directoryName);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return true;
            } else {
                return dir.delete() ? dir.mkdirs() : false;
            }
        } else {
            return dir.mkdirs();
        }
    }

    /**
     * 删除文件或目录。
     * 如果是目录，删除时会先删除目录下的所有文件或目录。
     *
     * @param tree 目录树。可为单个文件
     * @return boolean
     */
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

    /**
     * 删除文件或目录。
     * 如果是目录，删除时会先删除目录下的所有文件或目录。
     *
     * @param path 目录树。可为单个文件
     * @return boolean
     */
    public static boolean deleteTree(String path) {
        File file = new File(path);
        return deleteTree(file);
    }

    /**
     * 复制文件。
     *
     * @param srcFile    原文件
     * @param targetFile 目标文件
     * @return boolean
     */
    public static boolean copy(File srcFile, File targetFile) {
        boolean success = false;
        if (srcFile.exists()) { //文件存在时
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(srcFile); //读入原文件
                outputStream = new FileOutputStream(targetFile);
                long copied = IOUtil.copyLarge(inputStream, outputStream);
                success = copied > -1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.close(outputStream);
                IOUtil.close(inputStream);
            }
        }
        return success;
    }

    /**
     * 复制文件。
     *
     * @param srcFile    原文件
     * @param targetFile 目标文件
     * @return boolean
     */
    public static boolean copy(String srcFile, String targetFile) {
        return copy(new File(srcFile), new File(targetFile));
    }

    /**
     * Returns the canonical pathname string of this abstract pathname.
     * <p>
     * <p> A canonical pathname is both absolute and unique.  The precise
     * definition of canonical form is system-dependent.  This method first
     * converts this pathname to absolute form if necessary, as if by invoking the
     * {@link File#getAbsolutePath} method, and then maps it to its unique form in a
     * system-dependent way.  This typically involves removing redundant names
     * such as <tt>"."</tt> and <tt>".."</tt> from the pathname, resolving
     * symbolic links (on UNIX platforms), and converting drive letters to a
     * standard case (on Microsoft Windows platforms).
     * <p>
     * <p> Every pathname that denotes an existing file or directory has a
     * unique canonical form.  Every pathname that denotes a nonexistent file
     * or directory also has a unique canonical form.  The canonical form of
     * the pathname of a nonexistent file or directory may be different from
     * the canonical form of the same pathname after the file or directory is
     * created.  Similarly, the canonical form of the pathname of an existing
     * file or directory may be different from the canonical form of the same
     * pathname after the file or directory is deleted.
     *
     * @return The canonical pathname string denoting the same file or
     * directory as this abstract pathname
     * @throws IOException       If an I/O error occurs, which is possible because the
     *                           construction of the canonical pathname may require
     *                           filesystem queries
     * @throws SecurityException If a required system property value cannot be accessed, or
     *                           if a security manager exists and its <code>{@link
     *                           java.lang.SecurityManager#checkRead}</code> method denies
     *                           read access to the file
     * @see Path#toRealPath
     * @since JDK1.1
     */
    public static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取系统中指定类型的盘符。
     *
     * @param driveType 盘符类型
     * @return File[]
     */
    public static File[] listRoots(int driveType) {
        if (OSUtil.isWindows()) {
            return listRootsForWindows(driveType);
        } else {
            return null;
        }
    }

    /**
     * 获取系统中指定类型的盘符。 ------ Windows
     *
     * @param driveType 盘符类型
     * @return File[]
     */
    public static File[] listRootsForWindows(int driveType) {
        List<File> rootList = new ArrayList<>();
        FileWriter fileWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            File scriptFile = File.createTempFile("listRoots", ".cmd");
            scriptFile.deleteOnExit();
            fileWriter = new FileWriter(scriptFile);

            StringBuilder scriptContent = new StringBuilder();
            scriptContent.append("Wmic Logicaldisk Where \"DriveType=").append(driveType).append("\" Get DeviceID");
            fileWriter.write(scriptContent.toString());
            fileWriter.flush();
            fileWriter.close();

            Process process = Runtime.getRuntime().exec(scriptFile.getPath());
            scriptFile.deleteOnExit();
            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, OSUtil.ENCODING);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Wmic Logicaldisk Where \"DriveType=")) {
                    continue;
                }
                if (line.contains("DeviceID")) {
                    continue;
                }
                if (StringUtil.isBlank(line)) {
                    continue;
                }
                File root = new File(line.trim(), File.separator);
                rootList.add(root);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.close(bufferedReader);
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
            IOUtil.close(fileWriter);
        }
        return rootList.toArray(new File[0]);
    }

    /**
     * Moves a filtered directory to a new location.
     * <p>
     * This method moves the contents of the specified source directory
     * to within the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist.
     * If the destination directory did exist, then this method merges
     * the source with the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the files' last modified
     * date/times using {@link File#setLastModified(long)}, however it is
     * not guaranteed that those operations will succeed.
     * If the modification operation fails, no indication is provided.
     * </p>
     * <h3>Example: Move directories only</h3>
     * <pre>
     *  // only move the directory structure
     *  FileUtils.moveDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
     *  </pre>
     * <p>
     * <h3>Example: Move directories and txt files</h3>
     * <pre>
     *  // Create a filter for ".txt" files
     *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
     *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
     *
     *  // Create a filter for either directories or ".txt" files
     *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
     *
     *  // Move using the filter
     *  FileUtils.moveDirectory(srcDir, destDir, filter, false);
     *  </pre>
     *
     * @param srcDir           an existing directory to move, must not be {@code null}
     * @param destDir          the new directory, must not be {@code null}
     * @param filter           the filter to apply, null means move all directories and files
     * @param preserveFileDate true if the file date of the move
     *                         should be the same as the original
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during moving
     * @since 1.4
     */
    public static void moveDirectory(final File srcDir, final File destDir, final FileFilter filter, final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        // Cater for destination being directory within the source directory (see IO-141)
        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<String>(srcFiles.length);
                for (final File srcFile : srcFiles) {
                    final File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doMoveDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
    }

    /**
     * Moves a filtered directory to a new location preserving the file dates.
     * <p>
     * This method moves the contents of the specified source directory
     * to within the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist.
     * If the destination directory did exist, then this method merges
     * the source with the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the files' last
     * modified date/times using {@link File#setLastModified(long)}, however
     * it is not guaranteed that those operations will succeed.
     * If the modification operation fails, no indication is provided.
     * </p>
     * <h3>Example: Move directories only</h3>
     * <pre>
     *  // only move the directory structure
     *  FileUtils.moveDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY);
     *  </pre>
     * <p>
     * <h3>Example: Move directories and txt files</h3>
     * <pre>
     *  // Create a filter for ".txt" files
     *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
     *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
     *
     *  // Create a filter for either directories or ".txt" files
     *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
     *
     *  // Move using the filter
     *  FileUtils.moveDirectory(srcDir, destDir, filter);
     *  </pre>
     *
     * @param srcDir  an existing directory to move, must not be {@code null}
     * @param destDir the new directory, must not be {@code null}
     * @param filter  the filter to apply, null means move all directories and files
     *                should be the same as the original
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during moving
     * @since 1.4
     */
    public static void moveDirectory(final File srcDir, final File destDir, final FileFilter filter) throws IOException {
        moveDirectory(srcDir, destDir, filter, true);
    }

    /**
     * Internal move file method.
     * This caches the original file length, and throws an IOException
     * if the output file length is different from the current input file length.
     * So it may fail if the file changes size.
     * It may also fail with "IllegalArgumentException: Negative size" if the input file is truncated part way
     * through moving the data and the new file size is less than the current position.
     *
     * @param srcFile          the validated source file, must not be {@code null}
     * @param destFile         the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException              if an error occurs
     * @throws IOException              if the output file length is not the same as the input file length after the
     *                                  move completes
     * @throws IllegalArgumentException "Negative size" if the file is truncated so that the size is less than the
     *                                  position
     */
    private static void doMoveFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }
        if (preserveFileDate) {
            srcFile.renameTo(destFile);
        } else {
            destFile.setLastModified(System.currentTimeMillis());
        }
    }

    /**
     * Internal move directory method.
     *
     * @param srcDir           the validated source directory, must not be {@code null}
     * @param destDir          the validated destination directory, must not be {@code null}
     * @param filter           the filter to apply, null means move all directories and files
     * @param preserveFileDate whether to preserve the file date
     * @param exclusionList    List of files and directories to exclude from the move, may be null
     * @throws IOException if an error occurs
     * @since 1.1
     */
    private static void doMoveDirectory(final File srcDir, final File destDir, final FileFilter filter, final boolean preserveFileDate, final List<String> exclusionList) throws IOException {
        // recurse
        final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) {  // null if abstract pathname does not denote a directory, or if an I/O error occurs
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else {
            if (!destDir.mkdirs() && !destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' directory cannot be created");
            }
        }
        if (!destDir.canWrite()) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (final File srcFile : srcFiles) {
            final File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doMoveDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                } else {
                    doMoveFile(srcFile, dstFile, preserveFileDate);
                }
            }
        }

        // Do this last, as the above has probably affected directory metadata
        if (preserveFileDate) {
            destDir.setLastModified(srcDir.lastModified());
        }
    }

    /**
     * checks requirements for file move
     *
     * @param src  the source file
     * @param dest the destination
     * @throws FileNotFoundException if the destination does not exist
     */
    private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }
}
