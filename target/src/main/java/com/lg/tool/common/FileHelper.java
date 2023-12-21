package com.lg.tool.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileHelper {
    private static Logger logger = LogManager.getLogger(FileHelper.class);
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private File fileObj;

    public FileHelper() {
    }

    public FileHelper(String filePath, String fileName) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                logger.error("mkdirs failed: " + dir.getAbsolutePath());
            }
        }
        File file = new File(filePath + File.separator + fileName);
//        logger.info("report path: " + file.getAbsolutePath());
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    logger.error("create file failed: " + file.getAbsoluteFile());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.fileObj = file;
        }
    }

    /**
     * 通过路径返回文件内容
     *
     * @param filePath resource目录中文件的路径
     * @return 内容列表
     */
    public List<String> listFileContent(String filePath) {
        //此处如果用File file = Resource.getFile(filePath)会报异常：找不到文件
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            return null;
        }
        List<String> listContent = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                listContent.add(line);
            }
            br.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listContent;
    }

    public static List<String> listFileContent(InputStream inputStream) {
        List<String> listContent = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                listContent.add(line);
            }
            br.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listContent;
    }

    /**
     * 获取resource目录中的 文件
     *
     * @param filePath resource目录下的文件路径
     * @param charSet  字符编码
     * @return 返回内容列表
     */
    public List<String> listFileContent(String filePath, String charSet) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            return null;
        }
        List<String> listContent = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, charSet));
            String line;
            while ((line = br.readLine()) != null) {
                listContent.add(line);
            }
            br.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listContent;
    }

    /**
     * 获取resource目录中的 文件
     *
     * @param inputStream 文件inputStream
     * @param charSet     字符编码
     * @return 返回内容列表
     */
    @NotNull
    public static List<String> listFileContent(InputStream inputStream, String charSet) {
        List<String> listContent = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, charSet));
            String line;
            while ((line = br.readLine()) != null) {
                listContent.add(line);
            }
            br.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listContent;
    }

    public static void writeToFile(String filePath, String fileName, String stringContent, boolean append) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                logger.error("mkdirs failed: " + dir.getAbsolutePath());
            }
        }
        File file = new File(filePath + File.separator + fileName);
//        logger.info("report path: " + file.getAbsolutePath());
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    logger.error("create file failed: " + file.getAbsoluteFile());
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), append));
            bufferedWriter.write(stringContent);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目录下的全部文件,跳过目录，并返回绝对路径
     *
     * @param dirPath 目录路径
     * @return 文件路径的List
     */
    public List<String> listFiles(String dirPath) {
        List<String> files = new ArrayList<>();
        File dir = new File(dirPath);
        File[] tempList = dir.listFiles();
        assert tempList != null;
        for (File fileObj : tempList) {
            if (fileObj.isFile()) {
                files.add(fileObj.getAbsolutePath());
            }
        }
        return files;
    }

    /**
     * 获取资源文件下，指定目录下的全部文件
     *
     * @param dirPath
     * @return
     */
    public List<String> listFilesFromResource(String dirPath) {
        String resourcePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(dirPath)).getPath();
        return this.listFiles(resourcePath);
    }

    public ConcurrentLinkedQueue<String> queueFiles(String dirPath) {
        ConcurrentLinkedQueue<String> files = new ConcurrentLinkedQueue<>();
        File dir = new File(dirPath);
        File[] tempList = dir.listFiles();
        assert tempList != null;
        for (File fileObj : tempList) {
            if (fileObj.isFile()) {
                files.offer(fileObj.getAbsolutePath());
            }
        }
        return files;
    }

    /**
     * 获取目录下的全部文件，并递归获取子目录下的全部文件，返回全部文件的绝对路径
     *
     * @param dirPath 目录路径
     * @param files   用来放入文件路径的列表
     */
    public void listAllFiles(String dirPath, ArrayList<String> files) {
        File dir = new File(dirPath);
        File[] tempList = dir.listFiles();
        assert tempList != null;
        for (File fileObj : tempList) {
            if (fileObj.isFile()) {
                files.add(fileObj.getAbsolutePath());
            } else {
                listAllFiles(fileObj.getAbsolutePath(), files);
            }
        }
    }

    public void write(String stringContent) {
        readWriteLock.writeLock().lock();
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(this.fileObj.getAbsoluteFile(), true), "UTF-8");
            out.write(stringContent);
            out.flush();
            out.close();
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileObj.getAbsoluteFile(), true));
//            bufferedWriter.write(stringContent);
//            bufferedWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static String getNameFromPath(String filePath) {
        File file = new File(filePath.trim());
        return file.getName();
    }

    public static void main(String[] args) {
        FileHelper fileHelper = new FileHelper();
        List<String> files = fileHelper.listFilesFromResource("test");
        System.out.println(files);
    }
}
