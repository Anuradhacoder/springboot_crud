package com.example.springboot_crud.controller;


import com.example.springboot_crud.exception.ResourceNotFoundException;
import com.example.springboot_crud.model.Employee;
import com.example.springboot_crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee)
    {
       return employeeRepository.save(employee);
    }

    //get Employee by id
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
      Employee employee=employeeRepository.findById(id).orElseThrow(() -> new
              ResourceNotFoundException("employee not exist with id:"+id));
      return ResponseEntity.ok(employee);
    }

    //update employee
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("employee not exist with id:"+id));
        employee.setFirst_name(employeeDetails.getFirst_name());
        employee.setLast_name(employeeDetails.getLast_name());
        employee.setEmail_id(employeeDetails.getEmail_id());
        Employee updatedEmployee=employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
    //delete employee
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("employee not exist with id:"+id));
        employeeRepository.delete(employee);
        Map<String,Boolean> response=new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
