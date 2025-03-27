package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.EmployeeDto;
import com.ruso.zapicito.dto.EmployeeServiceDto;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.EmployeeService;
import com.ruso.zapicito.service.ServicesService;
import com.ruso.zapicito.util.ResponseUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ServicesService servicesService;

    public EmployeeController(@Lazy EmployeeService employeeService,
                              @Lazy ServicesService servicesService) {
        this.employeeService = employeeService;
        this.servicesService = servicesService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody EmployeeDto employeeDto ) throws ZapicitoException {
        Employee employee = employeeService.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeService.saveEmployee(employee, employeeDto.getLocation(), employeeDto.getRole());

        return new ResponseEntity<>(ResponseUtil.success(savedEmployee), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> findEmployee(@PathVariable Long id) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(ResponseUtil.success(employee), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> findAllEmployeesByBranchId(@RequestParam Long branchId) {
        List<Employee> employees = employeeService.findEmployeesByBranchId(branchId);
        return new ResponseEntity<>(ResponseUtil.success(employees), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                                                @PathVariable Long id) throws ZapicitoException {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(ResponseUtil.success(updatedEmployee), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/detach")
    public ResponseEntity<ApiResponse<String>> detachEmployee(@PathVariable Long id ) {

        employeeService.detachEmployee(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/attach")
    public ResponseEntity<ApiResponse<String>> attachEmployee(@PathVariable Long id ) {

        employeeService.attachEmployee(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services")
    public ResponseEntity<ApiResponse<String>> connectServiceToEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeServiceDto employeeServiceDto) throws ZapicitoException {

        servicesService.connectServiceToEmployee(id, employeeServiceDto.getServices());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services/all")
    public ResponseEntity<ApiResponse<String>> connectAllServicesToEmployee(@PathVariable Long id) throws ZapicitoException {
        servicesService.connectAllServicesToEmployee(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<ApiResponse<List<Service>>> getAllServicesByEmployee(@PathVariable Long id) throws ZapicitoException {
        return new ResponseEntity<>(ResponseUtil.success(servicesService.getAllServicesByEmployee(id)), HttpStatus.ACCEPTED);
    }

}
