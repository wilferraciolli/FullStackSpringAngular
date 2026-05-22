package com.witech.dataexplore.graphql.person;

import com.witech.dataexplore.graphql.SortOrder;

public class PersonSort {
    private PersonSortField field;
    private SortOrder order;

    public PersonSortField getField() {
        return field;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setField(PersonSortField field) {
        this.field = field;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }
}
