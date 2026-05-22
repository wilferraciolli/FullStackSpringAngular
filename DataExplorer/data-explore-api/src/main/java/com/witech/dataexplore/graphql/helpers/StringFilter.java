package com.witech.dataexplore.graphql.helpers;

public class StringFilter {
    private String equals;
    private String notEquals;
    private String contains;
    private String startsWith;
    private Boolean isNull;

    public String getEquals() {
        return equals;
    }

    public void setEquals(String equals) {
        this.equals = equals;
    }

    public String getNotEquals() {
        return notEquals;
    }

    public void setNotEquals(String notEquals) {
        this.notEquals = notEquals;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public Boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(Boolean aNull) {
        isNull = aNull;
    }
}
