package com.treeyh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author TreeYH
 * @version 1.0
 * @description 流工具类
 * @create 2019-05-17 18:06
 */
public class StreamUtils {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 通过输入流，将其转换成字节byte数组
     *
     * @param is
     *            输入的数据流
     * @return byte[] 转化后的字节数组
     */
    public static byte[] getByteByStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int length = 0;
        while (length != -1) {
            try {
                length = is.read(b);
            } catch (IOException e) {
                logger.error(e.getMessage(), logger);
            }
            if (length != -1) {
                baos.write(b, 0, length);
            }
        }
        if (is != null) {
            try {
                is.close();
                is = null;
            } catch (IOException e) {
                logger.error(e.getMessage(), logger);
            }

        }
        return baos.toByteArray();
    }

    /**
     * inputstream 转换为string
     * @param is
     * @param charset
     * @return
     * @throws IOException
     */
    public static String getStringByStream(InputStream is, String charset) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        String str = result.toString(charset); //        StandardCharsets.UTF_8.name()
        return str;
    }
}
