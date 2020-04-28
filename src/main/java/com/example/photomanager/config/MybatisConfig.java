package com.example.photomanager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author alex
 */

@SpringBootConfiguration
@MapperScan(basePackages = "com.example.photomanager.mapper")
public class MybatisConfig {
}
