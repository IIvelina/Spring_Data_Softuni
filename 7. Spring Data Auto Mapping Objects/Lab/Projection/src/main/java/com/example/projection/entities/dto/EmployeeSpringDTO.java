package com.example.projection.entities.dto;

import java.math.BigDecimal;

public class EmployeeSpringDTO {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String managerLastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + salary + " - "
                + "Manager: "
                + (managerLastName == null?"[no manager]" : managerLastName);
    }
}
