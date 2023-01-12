package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.BranchServices;
import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.repository.BranchRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public BranchService(BranchRepository branchRepository, CompanyService companyService, EmployeeService employeeService) {
        this.branchRepository = branchRepository;
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    public Branch createBranch(Branch branch, Long companyId) throws ZapicitoException {
        Company company = companyService
                .findCompanyById(companyId);

        branch.setCompany(company);
        return branchRepository.save(branch);
    }

    public Branch findBranchById(Long id) throws ZapicitoException {
        return branchRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find branch by ID="+id));
    }

    public List<Branch> findAllBranches(){
        return branchRepository.findAll();
    }

    public Branch updateBranch(BranchDto updatedBranch, Long id) throws ZapicitoException {

        return branchRepository.findById(id)
                .map(branch -> {
                    branch.setName(updatedBranch.getName());
                    branch.setDescription(updatedBranch.getDescription());
                    branch.setAddress(updatedBranch.getAddress());
                    branch.setPhone(updatedBranch.getPhone());
                    return branchRepository.saveAndFlush(branch);
                })
                .orElseThrow(()->new ZapicitoException("Couldn't find branch by ID="+id));

    }

    public void deleteBranch(Long id){
        branchRepository.deleteById(id);
    }

    @Transactional
    public void updateBranch(Branch branch) throws ZapicitoException {
        branchRepository.save(branch);
    }

    public void connectEmployeeToBranch(Long branchId, BranchEmployeeDto branchEmployeeDto) throws ZapicitoException {
        Branch branch = findBranchById(branchId);
        Employee employee = employeeService.findEmployeeById(branchEmployeeDto.getEmployeeId());

        if(branchEmployeeDto.isActive()){
           branch.getEmployees().add(employee);
        }else {
           branch.getEmployees().remove(employee);
        }

        updateBranch(branch);
    }

    public void connectAllEmployeesToBranch(Long branchId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        Branch branch = findBranchById(branchId);

        if (branchServiceDto.isActive()) {
            List<Employee> employees = employeeService.findAllEmployees();
            branch.getEmployees().addAll(employees);
        }
        else {
            branch.getEmployees().clear();
        }
        updateBranch(branch);
    }

}
