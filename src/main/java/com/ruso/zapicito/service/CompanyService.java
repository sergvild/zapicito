package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company){
        return companyRepository.save(company);
    }

    public Company findCompanyById(Long id) throws ZapicitoException {
        return companyRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find company by ID="+id));
    }

    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public Company updateCompany(CompanyDto updatedCompany, Long id) throws ZapicitoException {

        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setDescription(updatedCompany.getDescription());
                    company.setAddress(updatedCompany.getAddress());
                    company.setPhone(updatedCompany.getPhone());
                    return companyRepository.saveAndFlush(company);
                })
                .orElseThrow(()->new ZapicitoException("Couldn't find company by ID="+id));

    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }

}
