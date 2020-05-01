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

/**
 *   七牛云工具类
 *    @author gsj
 */
public class QiniuUtils {

    private static String accessKey = "-IB_j7KoQYN5WL2MgXb8rNT8EG6XaOmdBF5QDqdw";

    private static String secretKey = "5_gug7YPeU-VTKHbeQNimc84iwRckZUt8bAVW5pS";

    private static String bucket = "photomanager1";

    private static String baseUrl = "http://9n4jxxma.bkt.clouddn.com";
    /**
     *  上传凭证有效期 10min
     */
    private static long expireSeconds = 600;

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
     * @param name 图片名称
     * @return  图片存储的url
     */
    public static String uploadPhoto(File file,String name) {
        Configuration cfg = new Configuration(Region.region0());

        UploadManager uploadManager = new UploadManager(cfg);
        try {
            InputStream inputStream = new FileInputStream(file);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket,name,expireSeconds,null);
            Response response = uploadManager.put(inputStream,name,upToken,null, null);
        } catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        return baseUrl + "/" + name;
    }



}
