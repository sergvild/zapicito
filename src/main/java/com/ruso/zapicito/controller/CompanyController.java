package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.CompanyService;
import com.ruso.zapicito.service.EmployeeService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto companyDto){
        Company company = new Company(companyDto);
        return new ResponseEntity<>(companyService.createCompany(company), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Company> findCompany(@RequestParam @ApiParam(name = "id", value = "Company id", example = "1")Long id) throws ZapicitoException {
        Company company = companyService.findCompanyById(id);
        return new ResponseEntity<>(company, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> findAllCompanies(){
        List<Company> companies = companyService.findAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDto updatedCompany, @PathVariable @ApiParam(name = "id", value = "Company id", example = "1") Long id) throws ZapicitoException {
        Company company = companyService.updateCompany(updatedCompany, id);
        return new ResponseEntity<>(company, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable @ApiParam(name = "id", value = "Company id", example = "1") Long id){
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
