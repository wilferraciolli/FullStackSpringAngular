package com.wiltech.blog.posts;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wiltech.blog.core.Link;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonRootName("post")
public class PostDTO {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonIgnore
    private Long userId;

    //    @JsonFormat(pattern = "yyyy-MM-ddT00:00:00Z")
    private LocalDateTime creationDateTime;

    @NotEmpty
    private String title;

    @NotNull
    private String content;

    private List<String> tags;

    private Integer likesCount;

    private List<Link> links;
}
