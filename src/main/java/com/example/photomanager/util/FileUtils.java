package com.example.photomanager.util;

import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.qiniu.util.UrlUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  文件操作工具类
 * @author gsj
 */

public class FileUtils {
    /**
     *  @return 从 url连接中获取文件名
     *  eg: http://qiuzhao.com/index.html?tag=1 ---> index.html
     */
    public static String getFileNameFromUrl(String url) {
        String[] strs = url.split("/");
        String s = strs[strs.length-1];
        return s.substring(0,s.indexOf('?')==-1?s.length():s.indexOf('?'));
    }

    /**
     * 若目录不存在,则创建
     */
    public static void createDir(String dirName) {
        File file = new File(dirName);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     *  将文件【写入】的简单实现
     */
    public static void fileInput(InputStream in, OutputStream out) {
        byte[] bytes = new byte[1024];
        int len = -1;
        try {
            while ((len=in.read(bytes))!=-1){
                out.write(bytes,0,len);
                System.out.println("写入了:"+ len);
            }
            out.flush();
        }catch (IOException e){
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
            }
        }
    }

    /**
     *  简单版:检验图片格式是否正确
     */
    public static boolean checkPictureFormat(String fileName){
        int beginIndex = fileName.lastIndexOf('.');
        int endIndex = fileName.length();
        String fileFormat = fileName.substring(beginIndex,endIndex);
        if (fileFormat.equals(".png") || fileFormat.equals(".jpg") ||
                fileFormat.equals(".jpeg") || fileFormat.equals(".gif")){
            return true;
        }
        return false;
    }

}
