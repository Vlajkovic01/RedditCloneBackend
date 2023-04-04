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
@Document(indexName = "communities")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class IndexedCommunity {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Nested)
    private List<String> rules;
}
