package com.witech.dataexplore.graphql;

import org.springframework.data.domain.Page;
import java.util.List;

/**
 * Generic pagination envelope reused across all GraphQL queries.
 *
 * The GraphQL schema still needs a concrete named type per entity
 * (e.g. PersonPage, AddressPage) because GraphQL has no generics.
 * But on the Java side this single class covers everything.
 */
public class PageResult<T> {

    private final List<T> content;
    private final long    totalElements;
    private final int     totalPages;
    private final int     page;
    private final int     size;

    /** Build directly from a Spring Data Page. */
    public static <T> PageResult<T> of(Page<T> springPage) {
        return new PageResult<>(
                springPage.getContent(),
                springPage.getTotalElements(),
                springPage.getTotalPages(),
                springPage.getNumber(),
                springPage.getSize()
        );
    }

    public PageResult(List<T> content, long totalElements, int totalPages, int page, int size) {
        this.content       = content;
        this.totalElements = totalElements;
        this.totalPages    = totalPages;
        this.page          = page;
        this.size          = size;
    }

    public List<T> getContent()      { return content; }
    public long getTotalElements()   { return totalElements; }
    public int  getTotalPages()      { return totalPages; }
    public int  getPage()            { return page; }
    public int  getSize()            { return size; }
}

