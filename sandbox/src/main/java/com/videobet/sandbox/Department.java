package com.videobet.sandbox;

import java.util.List;

public class Department {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getTotalSalary(List<Employee> employees, int departmentId) {
        return employees.stream().filter(e -> departmentId == e.getDepartmentId())
                .mapToLong(Employee::getSalary).sum();
    }
}
