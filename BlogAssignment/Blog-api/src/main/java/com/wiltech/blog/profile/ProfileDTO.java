package com.wiltech.blog.profile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.wiltech.blog.core.Link;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonRootName("profile")
public class ProfileDTO {

    private Long id;
    private List<Link> links;
}
