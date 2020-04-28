package com.example.photomanager.util;


import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * 拼接 URL 参数
 * @author alex
 */
public class UrlUtils {
    public static String addParameterMap(String url, Map<String, String> params) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(url);
        params.forEach(urlBuilder::queryParam);
        return urlBuilder.build().toString();
    }

    public static String addParameter(String url, String name, String value) {
        return UriComponentsBuilder
                .fromUriString(url)
                .queryParam(name, value)
                .build()
                .toString();
    }
}
