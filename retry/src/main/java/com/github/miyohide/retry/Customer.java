package com.github.miyohide.retry;

import java.time.OffsetDateTime;

public class Customer {
    private long id;
    private String firstName, lastName;
    private OffsetDateTime created_at;

    public Customer(long id, String firstName, String lastName, OffsetDateTime created_at) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
