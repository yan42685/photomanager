package com.example.photomanager.util;

import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *   七牛云工具类
 *    @author gsj
 */
public class QiniuUtils {

    private static String accessKey = "-IB_j7KoQYN5WL2MgXb8rNT8EG6XaOmdBF5QDqdw";

    private static String secretKey = "5_gug7YPeU-VTKHbeQNimc84iwRckZUt8bAVW5pS";

    private static String bucket = "photomanager1";

    private static String baseUrl = "http://q9n4jxxma.bkt.clouddn.com/";

    /**
     *  上传凭证有效期 10min
     */
    private static long expireSeconds = 600;

    /**
     *  默认下载路径
     */
    private static String downloadDir = "D:/photos/";


    /**
     *  生成文件上传凭证
     */
    public static String createUploadToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,null,expireSeconds,null);
        return upToken;
    }

    /**
     * 上传图片
     * @param file 文件
     * @return  图片存储的url
     */
    public static String uploadPhoto(File file) {
        Configuration cfg = new Configuration(Region.region0());
        String name = file.getName();
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            InputStream inputStream = new FileInputStream(file);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket,name,expireSeconds,null);
            Response response = uploadManager.put(inputStream,name,upToken,null, null);
        } catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        return baseUrl + name;
    }

    /**
     * @param url : 图片链接
     * 默认下载路径 : d:/photos/
     * 文件的下载,待实现:多线程下载 , 代码待优化
     */
    public static void downloadPhoto(String url){
        String[] strs = url.split("/");
        // 得到文件名
        String name = strs[strs.length-1];
        InputStream in = null;
        OutputStream out = null;
        try{
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            in = connection.getInputStream();

            File dir = new File(downloadDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(downloadDir + name);
            out = new FileOutputStream(file);

            byte[] b = new byte[1024];
            int len = -1;
            while ((len=in.read(b))!=-1){
                out.write(b,0,len);
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
        System.out.println("下载成功");
    }

}
