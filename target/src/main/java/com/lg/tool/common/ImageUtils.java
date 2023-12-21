package com.lg.tool.common;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageUtils {

    public static String getBase64Image(InputStream inputStream) {
        byte[] data = null;
        try {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }

    /**
     * 获取图片base64字符串
     *
     * @param imageName 绝对路径
     * @return
     */
    public static String getBase64FromPath(String imageName) {
        byte[] data = null;
        try {
            InputStream inputStream = new FileInputStream(imageName);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }


    public String getBase64ImageFromResource(String imageName) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(imageName);
            if (inputStream == null) {
                return null;
            }
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Objects.requireNonNull(Base64.encodeBase64(data)));
    }

    public byte[] listByteImageFromResource(String imageName) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(imageName);
            if (inputStream == null) {
                return null;
            }
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public byte[] listByteImageFromPath(String imageName) {
        InputStream inputStream;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imageName);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 将字节数组分成N等分
     *
     * @param resource 源数组
     * @param count    等分数
     * @return 等分后的字节数组列表
     */
    public static List<byte[]> byteSplit(byte[] resource, int count) {
        int len = resource.length / count;
        List<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < count - 1; i++) {
            byte[] newByte = new byte[len];
            System.arraycopy(resource, i * len, newByte, 0, len);
            bytes.add(newByte);
        }
        int newLen = resource.length - len * (count - 1);
        byte[] newByte = new byte[newLen];
        System.arraycopy(resource, newLen, newByte, 0, newLen);
        bytes.add(newByte);
        return bytes;
    }
}
