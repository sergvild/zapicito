package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Company;

import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.CompanyService;
import com.ruso.zapicito.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Company>> createCompany(@RequestBody CompanyDto companyDto,
                                                              @RequestParam Long userId) throws ZapicitoException {
        Company company = companyService.mapToCompany(companyDto);

        Company savedCompany = companyService.createCompany(company, userId);
        return new ResponseEntity<>(ResponseUtil.success(savedCompany), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> findCompany(@PathVariable Long id) throws ZapicitoException {
        Company company = companyService.findCompanyById(id);
        return new ResponseEntity<>(ResponseUtil.success(company), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompany(@RequestBody CompanyDto companyDto,
                                                              @PathVariable Long id) throws ZapicitoException {
        Company updatedCompany = companyService.mapToCompany(companyDto);

        Company company = companyService.updateCompany(updatedCompany, id);
        return new ResponseEntity<>(ResponseUtil.success(company), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
