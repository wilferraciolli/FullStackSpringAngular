package com.witech.dataexplore.graphql.addresses;
import java.util.List;
/** GraphQL response envelope for paginated Address results. */
public class AddressPage {
    private final List<AddressView> content;
    private final long totalElements;
    private final int  totalPages;
    private final int  page;
    private final int  size;
    public AddressPage(List<AddressView> content, long totalElements, int totalPages, int page, int size) {
        this.content       = content;
        this.totalElements = totalElements;
        this.totalPages    = totalPages;
        this.page          = page;
        this.size          = size;
    }
    public List<AddressView> getContent()      { return content; }
    public long getTotalElements()             { return totalElements; }
    public int  getTotalPages()                { return totalPages; }
    public int  getPage()                      { return page; }
    public int  getSize()                      { return size; }
}
