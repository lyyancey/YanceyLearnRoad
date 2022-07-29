package com.yancey.elasticsearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName = "hotel")
@Data
public class Hotel {
    @Id
    String id; // 对应文档的id
    String index; // 对应索引的名称
    Float score; // 对应文档的得分
    String title; // 对应索引中的title
    String city; // 对应索引中的city
    Double price; // 对应索引中的price
}
