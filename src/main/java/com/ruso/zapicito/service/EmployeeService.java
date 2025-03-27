package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.EmployeeDto;
import com.ruso.zapicito.dto.RoleType;
import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.entity.*;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.EmployeeMapper;
import com.ruso.zapicito.repository.EmployeeRepository;
import com.ruso.zapicito.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;
    @Lazy
    private final BranchService branchService;
    private final ScheduleService scheduleService;

    public Employee saveOwnerEmployee(UserDto userDto) throws ZapicitoException {
        Employee employee = mapToEmployee(userDto);
        employee.setUuid(UUID.randomUUID().toString());
        setAdminRole(employee);

        return employeeRepository.save(employee);
    }

    public Employee saveEmployee(Employee employee, Long location, Long roleId) throws ZapicitoException {
        employee.setUuid(UUID.randomUUID().toString());
        setEmployeeRole(employee, roleId);
        setEmployeeBranches(employee, location);

        Employee createdEmployee = employeeRepository.save(employee);
        setEmployeeCalendar(employee, location);

        return createdEmployee;
    }

    private void setEmployeeBranches(Employee employee, Long location) throws ZapicitoException {
        Branch branch = branchService.findBranchById(location);
        employee.getBranches().add(branch);
    }

    private void setAdminRole(Employee employee) throws ZapicitoException {
        Role role = roleRepository.findByName(RoleType.ADMIN)
                .orElseThrow(() -> new ZapicitoException("There is no an role with that id: "
                        + RoleType.ADMIN));

        employee.setRole(role);
    }

    private void setEmployeeRole(Employee employee, Long roleId) throws ZapicitoException {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ZapicitoException("There is no an role with that id: "
                        + roleId));

        employee.setRole(role);
    }

    private void setEmployeeCalendar(Employee employee, Long locationId) {
        Optional<Schedule> schedule = scheduleService.findScheduleByEmployeeIdAndBranchId(employee.getId(), locationId);
        if (schedule.isPresent()) {
            return;
        }

        Schedule newSchedule = new Schedule();
        newSchedule.setEmployeeId(employee.getId());
        newSchedule.setBranchId(locationId);

        scheduleService.createSchedule(newSchedule);
    }

    public Employee findEmployeeById(Long id) throws ZapicitoException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ZapicitoException("Couldn't find employee by ID=" + id));
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeesByBranchId(Long branchId) {
        return employeeRepository.findEmployeesByBranchId(branchId);
    }

    public Employee updateEmployee(Long employeeId, EmployeeDto employeeDto) throws ZapicitoException {
        Employee savedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find employee by ID=" + employeeId));

        employeeMapper.updateFromDto(employeeDto, savedEmployee);

        setEmployeeRole(savedEmployee, employeeDto.getRole());
        setEmployeeBranches(savedEmployee, employeeDto.getLocation());

        return employeeRepository.save(savedEmployee);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void detachEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Couldn't find employee by ID=" + employeeId));

        employee.setEnabled(false);
        employeeRepository.save(employee);
    }

    public void attachEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Couldn't find employee by ID=" + employeeId));

        employee.setEnabled(true);
        employeeRepository.save(employee);
    }

    private boolean emailExists(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

    public Employee mapToEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.fromDto(employeeDto);
        employee.setName(employeeDto.getFirstName() + " " + employeeDto.getLastName());

        return employee;
    }

    public Employee mapToEmployee(UserDto userDto) {
        Employee employee = employeeMapper.fromDto(userDto);
        employee.setName(userDto.getFirstName() + " " + userDto.getLastName());

        return employee;
    }
}
