package com.wiltech.blog.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Link {

    private String name;
    private String href;

}
