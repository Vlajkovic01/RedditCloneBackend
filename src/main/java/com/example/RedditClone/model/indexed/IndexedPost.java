package com.example.RedditClone.model.indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Document(indexName = "posts")
public class IndexedPost {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Text)
    private List<String> comments;
}
