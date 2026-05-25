package com.witech.dataexplore.graphql.person;

import com.witech.dataexplore.graphql.helpers.DateFilter;
import com.witech.dataexplore.graphql.helpers.StringFilter;

public class PersonFilter {
    private StringFilter email;
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter jobTitle;
    private DateFilter startDate;

    private DateFilter effectiveDate;

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(StringFilter jobTitle) {
        this.jobTitle = jobTitle;
    }

    public DateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(DateFilter startDate) {
        this.startDate = startDate;
    }

    public DateFilter getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(DateFilter effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
