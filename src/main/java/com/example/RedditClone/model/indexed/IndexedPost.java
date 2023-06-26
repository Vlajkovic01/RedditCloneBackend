package com.example.RedditClone.model.indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Document(indexName = "posts")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class IndexedPost {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private Integer communityId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Text)
    private String pdfText;

    @Field(type = FieldType.Text)
    private List<String> comments;

    @Field(type = FieldType.Text)
    private String flair;

    @Field(type = FieldType.Integer)
    private Integer karma;
}
