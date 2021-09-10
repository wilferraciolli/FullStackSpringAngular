package com.wiltech.blog.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Tag {

    private Long id;
    private String description;

}
