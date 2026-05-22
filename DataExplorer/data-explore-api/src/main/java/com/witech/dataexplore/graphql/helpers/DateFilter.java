package com.witech.dataexplore.graphql.helpers;

public class DateFilter {
    private String equals;
    private String before;
    private String after;
    private Boolean isNull;

    public String getEquals() {
        return equals;
    }

    public void setEquals(String equals) {
        this.equals = equals;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(Boolean aNull) {
        isNull = aNull;
    }
}
