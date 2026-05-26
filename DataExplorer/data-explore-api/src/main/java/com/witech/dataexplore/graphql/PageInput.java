package com.witech.dataexplore.graphql;
/** Bound from the GraphQL 'page' argument — page is 0-based. */
public class PageInput {
    private int page = 0;
    private int size = 20;
    public int getPage() { return page; }
    public void setPage(int page) { this.page = Math.max(0, page); }
    public int getSize() { return size <= 0 ? 20 : size; }
    public void setSize(int size) { this.size = size; }
}
