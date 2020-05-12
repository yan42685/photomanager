package com.example.photomanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.photomanager.bean.dto.*;
import com.example.photomanager.bean.entity.Photo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.example.photomanager.mapper.PhotoESMapper;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.AlbumService;
import com.example.photomanager.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photomanager.util.FileUtils;
import com.example.photomanager.util.QZ_IdUtils;
import com.example.photomanager.util.QiniuUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 17:18
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Autowired
    PhotoMapper photoMapper;

    @Autowired
    PhotoESMapper photoESMapper;

    @Autowired
    AlbumService albumService;

    @Override
    public List<PhotoInfo> fuzzyQuery(String message) {

        List<PhotoESInfo> photoESInfos = fuzzyQueryES(message);
        //封装成VO对象
        List<PhotoInfo> photoInfos = new LinkedList<>();
        for (PhotoESInfo i : photoESInfos) {
            photoInfos.add(queryById(i.getPhotoId()));
        }
        return photoInfos;
    }

    @Override
    public List<PhotoESInfo> fuzzyQueryES(String message) {
        //获取当前用户id
        Long currentUserid = 1L;
        return photoESMapper.findByUserIdAndDescLike(currentUserid, message);
    }

    @Override
    public Boolean addOrUpdatePhotoToES(PhotoESInfo photoESInfo) {
        photoESMapper.save(photoESInfo);
        return true;
    }

    @Override
    public Boolean deletePhotoToES(Long photoId) {
        photoESMapper.deleteById(photoId);
        return true;
    }


    @Override
    public List<PhotoInfo> query(Long albumId) {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.eq("album_id", albumId).eq("is_recycle", false);
        List<Photo> list = list(wrapper);
        LinkedList<PhotoInfo> infoLinkedList = new LinkedList<>();
        for (Photo i : list) {
            infoLinkedList.add(PhotoInfo.parsePhoto(i));
        }
        return infoLinkedList;
    }


    @Override
    public PhotoInfo queryById(Long id) {
        Photo photo = getById(id);
        return PhotoInfo.parsePhoto(photo);
    }


    @Override
    public Boolean modifyPhoto(PhotoInfo photoInfo) {
        //更新数据库
        Photo photo = getById(photoInfo.getId());
        photo.setName(photoInfo.getName()).setUpdateTime(LocalDateTime.now());
        boolean b = updateById(photo);
        //更新ES
        PhotoESInfo esInfo = PhotoESInfo.builder().photoId(photo.getId())
                .userId(photo.getUserId())
                .desc(photo.getName()).build();
        Boolean photoToES = addOrUpdatePhotoToES(esInfo);
        return b && photoToES;
    }


    /**
     * 上传图片,待完善:断电上传,多线程上传
     *
     * @return true代表上传成功, false代表上传失败
     */
    @Override
    public Boolean uploadPhoto(UploadInfo uploadInfo) {
        String fileName = uploadInfo.getFile().getOriginalFilename();
        if (!FileUtils.checkPictureFormat(fileName)) {
            throw new KnownException(ExceptionEnum.IMAGE_UPLOAD_FAIL);
        }
        String url = null;
        try {
            url = QiniuUtils.uploadPhoto(uploadInfo.getFile().getBytes(),fileName);
        } catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        Photo photo = new Photo();
        BeanUtils.copyProperties(uploadInfo, photo);
        photo.setUrl(url);
        photo.setCreateTime(LocalDateTime.now());
        photo.setUpdateTime(LocalDateTime.now());
        photo.setImageKey(fileName);
        if (photoMapper.insert(photo) > 0) {
            // 同步es
            PhotoESInfo esInfo = new PhotoESInfo();
            esInfo.setDesc(photo.getName());
            esInfo.setPhotoId(photo.getId());
            esInfo.setUserId(photo.getUserId());
            addOrUpdatePhotoToES(esInfo);

            //更新封面
            albumService.updateAlbumCover(photo.getAlbumId(),photo.getUrl());
            return true;
        }
        return false;
    }

    /**
     * 下载图片
     *
     * @param id 图片id
     */
    @Override
    public Boolean downloadPhoto(Long id, HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try{
            String url = photoMapper.selectById(id).getUrl();
            String key = photoMapper.selectById(id).getImageKey();
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            in = connection.getInputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachement;filename="+key);
            out = response.getOutputStream();

            byte[] Buffer = new byte[2048];
            int size = 0;
            while((size=in.read(Buffer)) != -1){
                out.write(Buffer, 0, size); //将每次读取到的数据写入客户端
            }
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
        return true;
    }

    /**
     * 删除图片到回收站
     */
    @Override
    public Boolean deletePhoto(Long id) {
        Photo photo = photoMapper.selectById(id);
        photo.setIsRecycle(true);
        photoMapper.updateById(photo);
        return true;
    }

    /**
     * 删除多张图片到回收站
     */
    @Override
    public Boolean deletePhotos(List<Long> ids) {
        List<Photo> photos = photoMapper.selectBatchIds(ids);
        for (Photo photo : photos) {
            photo.setIsRecycle(true);
            photoMapper.updateById(photo);
        }
        return true;
    }

    /**
     * @return 从回收站中删除一张图片
     */
    @Override
    public Boolean deletePhotoFromRecycleBin(Long id) {
        QiniuUtils.deletePhoto(photoMapper.selectById(id).getImageKey());
        if (photoMapper.deleteById(id) > 0 && deletePhotoToES(id)) {
            return true;
        }
        return false;
    }

    /**
     * @return 从回收站删除多张照片
     */
    @Override
    public Boolean deletePhotosFromRecycleBin(List<Long> ids) {
        List<String> imageKeys = photoMapper.getImageKeysByIds(ids);
        QiniuUtils.deletePhotos((imageKeys.toArray(new String[0])));
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            photoMapper.deleteById(id);
            deletePhotoToES(id);
        }
        return true;
    }

    /**
     * 还原一张图片
     */
    @Override
    public Boolean restorePhoto(Long id) {
        Photo photo = photoMapper.selectById(id);
        photo.setIsRecycle(false);
        photoMapper.updateById(photo);
        return true;
    }

    /**
     * 还原多张图片
     */
    @Override
    public Boolean restorePhotos(List<Long> ids) {
        List<Photo> photos = photoMapper.selectBatchIds(ids);
        for (Photo photo : photos) {
            photo.setIsRecycle(false);
            photoMapper.updateById(photo);
        }
        return true;
    }

    @Override
    public List<PhotoInfo> listAllPhotosInBin(Long userId) {
        return photoMapper.listAllPhotosInBin(userId);
    }
}
