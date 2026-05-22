package com.witech.dataexplore.graphql.addresses;

import com.witech.dataexplore.graphql.SortOrder;

public class AddressSort {
    private AddressSortField field;
    private SortOrder order;

    public AddressSortField getField() {
        return field;
    }

    public void setField(AddressSortField field) {
        this.field = field;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }
}
