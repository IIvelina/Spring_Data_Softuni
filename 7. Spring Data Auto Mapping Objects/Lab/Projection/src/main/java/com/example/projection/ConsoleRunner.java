package com.example.projection;

import com.example.projection.entities.Employee;
import com.example.projection.entities.dto.EmployeeSpringDTO;
import com.example.projection.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.persist();

        Optional<Employee> managerOp = this.employeeService.findOneById(2);
        Employee manager = managerOp.get();

        ModelMapper mapper = new ModelMapper();
        EmployeeSpringDTO dto = mapper.map(manager, EmployeeSpringDTO.class);

        System.out.println(dto);
    }

    private void persist() {
        Employee manager = new Employee(
                "Mrs.",
                "Manager",
                BigDecimal.ONE,
                LocalDate.now(),
                null
        );



        Employee fisrtEmployee = new Employee(
                "fitst",
                "last",
                BigDecimal.TEN,
                LocalDate.now(),
                manager
        );

        this.employeeService.save(fisrtEmployee);

        manager = this.employeeService
                .findOneById(fisrtEmployee.getManager().getId())
                .get();

        Employee secondEmployee = new Employee(
                "second",
                "last",
                BigDecimal.TEN,
                LocalDate.now(),
                manager
        );

        //    this.employeeService.save(secondEmployee);


    }
}
