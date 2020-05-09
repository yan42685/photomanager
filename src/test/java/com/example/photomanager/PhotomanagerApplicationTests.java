package com.example.photomanager;

import com.example.photomanager.bean.entity.Photo;
import com.example.photomanager.bean.vo.PhotoInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhotomanagerApplicationTests {

    @Test
    void contextLoads() {
        PhotoInfo info = PhotoInfo.builder().id(1L).name("11").build();
        info.setName("22");
        System.out.println(info);
    }

}
