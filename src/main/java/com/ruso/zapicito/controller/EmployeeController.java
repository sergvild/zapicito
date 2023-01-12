package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.dto.EmployeeBranchServiceDto;
import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.EmployeeService;
import com.ruso.zapicito.service.ServicesService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ServicesService servicesService;

    public EmployeeController(EmployeeService employeeService, ServicesService servicesService) {
        this.employeeService = employeeService;
        this.servicesService = servicesService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody UserDto userDto ) throws ZapicitoException {
        Employee employee = new Employee(userDto);
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.ACCEPTED);
    }

    @PostMapping("/admin")
    public ResponseEntity<Employee> createAdminEmployee(@RequestBody UserDto userDto) throws ZapicitoException {
        Employee employee = new Employee(userDto);
        return new ResponseEntity<>(employeeService.createAdminEmployee(employee), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Employee> findEmployee(@RequestParam Long id) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> findAllCompanies(){
        List<Employee> companies = employeeService.findAllEmployees();
        return new ResponseEntity<>(companies, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody UserDto updatedEmployee, @PathVariable @ApiParam(name = "id", value = "Employee id", example = "1") Long id) throws ZapicitoException {
        Employee employee = employeeService.updateEmployee(updatedEmployee, id);
        return new ResponseEntity<>(employee, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services/connect")
    public ResponseEntity<?> connectServiceToEmployee(@PathVariable @ApiParam(name = "id", value = "Employee id", example = "1") Long id, @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        servicesService.connectServiceToEmployee(id, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services/connect-all")
    public ResponseEntity<?> connectAllServicesToEmployee(@PathVariable @ApiParam(name = "id", value = "Employee id", example = "1") Long id, @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        servicesService.connectAllServicesToEmployee(id, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
