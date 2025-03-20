package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.CompanyMapper;
import com.ruso.zapicito.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CompanyService {

    private final EmployeeService employeeService;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService( @Lazy EmployeeService employeeService,
                          CompanyRepository companyRepository,
                          CompanyMapper companyMapper) {
        this.employeeService = employeeService;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Company createCompany(Company company, Long employeeId) throws ZapicitoException {
        company.setUuid(UUID.randomUUID().toString());
        Company savedCompany = companyRepository.save(company);

        Employee employee = employeeService.findEmployeeById(employeeId);
        employee.setCompany(savedCompany);
        employeeService.saveEmployee(employee);

        return savedCompany;

    }

    public Company findCompanyById(Long id) throws ZapicitoException {
        return companyRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find company by ID="+id));
    }

    public Company updateCompany(Company updatedCompany, Long companyId) throws ZapicitoException {

        companyRepository.findById(companyId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find company by ID="+companyId));

        updatedCompany.setId(companyId);
        updatedCompany.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        return companyRepository.save(updatedCompany);

    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }

    public Company mapToCompany(CompanyDto companyDto) {
        return companyMapper.fromDto(companyDto);
    }

}
