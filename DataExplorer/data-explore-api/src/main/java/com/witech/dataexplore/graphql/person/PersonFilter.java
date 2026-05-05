package com.witech.dataexplore.graphql.person;

public class PersonFilter {
    private String email;
    private String firstName;
    private String lastName;
    private String jobTitle;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobName() {
        return jobTitle;
    }

    public void setJobName(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
