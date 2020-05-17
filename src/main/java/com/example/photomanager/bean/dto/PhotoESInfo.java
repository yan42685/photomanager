package com.example.photomanager.bean.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author LuckyCurve
 * @date 2020/5/2 14:35
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "photo", type = "docs", shards = 1, replicas = 0)
public class PhotoESInfo implements Serializable {
    private static final long serialVersionUID = 8740516512694033572L;
    @Id
    private Long photoId;
    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Text)
    private String desc;
}
