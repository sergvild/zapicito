package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.CustomerDto;
import com.ruso.zapicito.entity.Customer;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.CustomerService;
import com.ruso.zapicito.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("companies/{companyId}")
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@PathVariable Long companyId,
                                                                @RequestBody CustomerDto customerDto ) throws ZapicitoException {

        Customer savedCustomer = customerService.saveCustomer(customerDto, companyId);

        return new ResponseEntity<>(ResponseUtil.success(savedCustomer), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> findCustomer(@PathVariable Long id) throws ZapicitoException {
        Customer customer = customerService.findCustomerById(id);
        return new ResponseEntity<>(ResponseUtil.success(customer), HttpStatus.ACCEPTED);
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<ApiResponse<List<Customer>>> findAllCustomersByCompanyId(@PathVariable Long companyId) {
        List<Customer> customers = customerService.findCustomersByCompanyId(companyId);
        return new ResponseEntity<>(ResponseUtil.success(customers), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable @ApiParam(name = "id", value = "Customer id", example = "1") Long id) throws ZapicitoException {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDto);
        return new ResponseEntity<>(ResponseUtil.success(updatedCustomer), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable @ApiParam(name = "id", value = "Customer id", example = "1") Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
