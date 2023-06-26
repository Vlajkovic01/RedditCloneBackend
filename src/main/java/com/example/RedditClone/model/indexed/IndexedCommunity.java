package com.example.RedditClone.model.indexed;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@Document(indexName = "communities")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class IndexedCommunity {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private List<String> rules;

    @Field(type = FieldType.Integer)
    private Integer numOfPosts;

    @Field(type = FieldType.Integer)
    private Integer avgKarma;

    @Field(type = FieldType.Text)
    private String pdfText;
}
