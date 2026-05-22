package com.witech.dataexplore.graphql.addresses;

import com.witech.dataexplore.graphql.helpers.StringFilter;

public class AddressFilter {
    private StringFilter city;
    private StringFilter street;

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getStreet() {
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }
}
