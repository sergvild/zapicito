package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.dto.UserLoginDto;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.EmployeeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final EmployeeService employeeService;


    public AuthController(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> registerUser(@RequestBody UserDto userDto) throws ZapicitoException {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            throw new ZapicitoException("Passwords don't match");
        }

        return new ResponseEntity<>( employeeService.saveOwnerEmployee(userDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {

        return new ResponseEntity<>( "User login successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<Employee> getCurrentUser(@RequestParam Long id) throws ZapicitoException {
        return new ResponseEntity<>( employeeService.findEmployeeById(id), HttpStatus.ACCEPTED);
    }

}
