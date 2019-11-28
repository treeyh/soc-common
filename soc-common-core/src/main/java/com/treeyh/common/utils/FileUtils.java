package com.treeyh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author TreeYH
 * @version 1.0
 * @description 描述, 参考 https://github.com/modouxiansheng/Doraemon/blob/f74b34ab3e59856714491fbf778e7626a2dc86de/aboutjava/src/main/java/aboutjava/file/FileCompress.java
 * @create 2019-11-28 19:05
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * zip压缩文件
     * @param zipPath
     * @param paths
     * @return 0 成功，1文件已存在，2压缩失败
     */
    public static Integer zipFilePip(String zipPath, List<String> paths) {

        File zipfile =new File(zipPath);
        if (zipfile.exists()) {
            return 1;
        }

        try(WritableByteChannel out = Channels.newChannel(new FileOutputStream(zipPath))) {
            long size = 0L;
            List<File> files = new ArrayList<>();
            for(String path : paths) {
                File file = new File(path);
                size += file.length();
                files.add(file);
            }

            Pipe pipe = Pipe.open();
            //异步任务
            CompletableFuture.runAsync(()->runTask(pipe, files));

            //获取读通道
            ReadableByteChannel readableByteChannel = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            while (readableByteChannel.read(buffer)>= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
            return 1;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return 2;
        }

    }

    //异步任务
    private static void runTask(Pipe pipe, List<File> files) {

        try(ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(pipe.sink()));
            WritableByteChannel out = Channels.newChannel(zos)) {
            System.out.println("Begin");
            for (File file : files) {
                System.out.println("start: " + file.getName());
                zos.putNextEntry(new ZipEntry(file.getName()));

                FileChannel jpgChannel = new FileInputStream(file).getChannel();
                jpgChannel.transferTo(0, file.length(), out);

                jpgChannel.close();
                System.out.println("end: " + file.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        String[] paths = new String[]{"/Users/tree/work/vps/my.cnf", "/Users/tree/work/vps/nginx.tar.gz", "/Users/tree/work/vps/phpstart.sh", "/Users/tree/work/vps/ssserver.sh"};
        String zipPath = "/Users/tree/work/vps/zip.zip";
        zipFilePip(zipPath, new ArrayList<>(Arrays.asList(paths)));
    }


}
