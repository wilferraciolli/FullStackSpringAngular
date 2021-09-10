package com.wiltech.blog.tags;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/tags")
public class TagRestService {

    @GetMapping("")
    public ResponseEntity<List<Tag>> getAllTags() {

        List<Tag> tags = IntStream.iterate(1, i -> i + 1)
                .limit(new Random().nextInt(6))
                .mapToObj(i -> new Tag(100L, "#tag" + i))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tags);
    }
}
