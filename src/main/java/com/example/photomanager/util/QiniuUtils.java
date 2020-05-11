package com.example.photomanager.util;

import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 七牛云工具类
 *
 * @author gsj
 */
public class QiniuUtils {

    private static String accessKey = "-IB_j7KoQYN5WL2MgXb8rNT8EG6XaOmdBF5QDqdw";

    private static String secretKey = "5_gug7YPeU-VTKHbeQNimc84iwRckZUt8bAVW5pS";

    private static String bucket = "photomanager1";

    private static String baseUrl = "http://q9n4jxxma.bkt.clouddn.com/";

    /**
     * 上传凭证有效期 10min
     */
    private static long expireSeconds = 600;

    /**
     * 默认下载路径
     */
    private static String downloadDir = "D:/photos/";


    /**
     * 生成文件上传凭证
     */
    public static String createUploadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, expireSeconds, null);
        return upToken;
    }

    /**
     * 上传图片
     *
     * @param file 文件
     * @return 图片存储的url
     */
    public static String uploadPhoto(File file) {
        Configuration cfg = new Configuration(Region.region0());
        String name = file.getName();
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            InputStream inputStream = new FileInputStream(file);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket, name, expireSeconds, null);
            Response response = uploadManager.put(inputStream, name, upToken, null, null);
        } catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        return baseUrl + name;
    }

    /**
     *  字节数组上传文件时调用该方法
     * @param key: 文件在七牛云中的存储索引
     */
    public static String uploadPhoto(byte[] bytes,String key) {
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket, key, expireSeconds, null);
            Response response = uploadManager.put(bytes, key, upToken);
        }catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        return baseUrl + key;
    }



    /**
     * 删除存储空间的文件
     *
     * @param key:待删除的文件在存储空间的索引
     */
    public static void deletePhoto(String key) {
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        System.out.println("删除成功");
    }

    /**
     * 批量删除文件
     */
    public static void deletePhotos(String[] names) {
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucket, names);
        try {
            bucketManager.batch(batchOperations);
        } catch (QiniuException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        System.out.println("删除成功");
    }
}
