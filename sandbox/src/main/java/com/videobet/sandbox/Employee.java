package com.videobet.sandbox;

public class Employee extends Person {
    private long salary;
    private int departmentId;

    public Employee(String lastName, String firstName, long salary, int departmentId) {
        super(lastName, firstName);
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
