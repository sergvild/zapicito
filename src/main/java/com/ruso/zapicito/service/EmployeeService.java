package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.dto.RoleType;
import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.BranchServices;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.entity.Role;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.repository.EmployeeRepository;
import com.ruso.zapicito.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    public Employee saveEmployee(Employee employee) throws ZapicitoException {
        setEmployeeRole(employee, RoleType.EMPLOYEE);
        return employeeRepository.save(employee);
    }

    public Employee createAdminEmployee(Employee employee) throws ZapicitoException {
        setEmployeeRole(employee, RoleType.ADMIN);
        return employeeRepository.save(employee);
    }

    private void setEmployeeRole(Employee employee, RoleType roleType) throws ZapicitoException {
        if (emailExists(employee.getEmail())) {
            throw new ZapicitoException("There is an account with that email address: "
                    + employee.getEmail());
        }

        Role role = roleRepository.findByName(roleType)
                .orElseThrow(()-> new ZapicitoException("There is no an role with that name: "
                        + RoleType.ADMIN.toString()) );

        employee.setRole(role);
    }
    public Employee findEmployeeById(Long id) throws ZapicitoException {
        return employeeRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find employee by ID="+id));
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(UserDto updatedEmployee, Long id) throws ZapicitoException {

        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(updatedEmployee.getFirstName());
                    employee.setLastName(updatedEmployee.getLastName());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setPhone(updatedEmployee.getPhone());
                    employee.setPassword(updatedEmployee.getPassword());
                    return employeeRepository.saveAndFlush(employee);
                })
                .orElseThrow(()->new ZapicitoException("Couldn't find employee by ID="+id));

    }

    public Employee updateEmployee(Employee employee) throws ZapicitoException {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    private boolean emailExists(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

}
