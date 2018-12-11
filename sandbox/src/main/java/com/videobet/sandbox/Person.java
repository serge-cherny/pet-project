package com.videobet.sandbox;

public class Person {
    private String lastName;
    private String firstName;

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
