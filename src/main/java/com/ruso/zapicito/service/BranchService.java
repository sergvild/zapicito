package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.BranchMapper;
import com.ruso.zapicito.repository.BranchRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyService companyService;
    private final BranchMapper branchMapper;
    private final ServicesService servicesService;

    public BranchService(BranchRepository branchRepository,
                         @Lazy CompanyService companyService,
                         BranchMapper branchMapper,
                         @Lazy ServicesService servicesService) {
        this.branchRepository = branchRepository;
        this.companyService = companyService;
        this.branchMapper = branchMapper;
        this.servicesService = servicesService;
    }

    public Branch createBranch(Branch branch, Long companyId) throws ZapicitoException {
        branch.setUuid(UUID.randomUUID().toString());
        Company company = companyService
                .findCompanyById(companyId);

        branch.setCompany(company);
        return branchRepository.save(branch);
    }

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);

    }

    public Branch findBranchById(Long id) throws ZapicitoException {
        return branchRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find branch by ID="+id));
    }

    public List<Branch> findBranchesByIds(List<Long> ids) {
        return branchRepository.findAllById(ids);
    }

    public List<Branch> findBranchesByCompanyId(Long companyId){
        return branchRepository.findBranchesByCompanyId(companyId);
    }

    public void deleteBranch(Long id){
        branchRepository.deleteById(id);
    }

    public Branch updateBranch(Branch updatedBranch, Long branchId) throws ZapicitoException {

        Branch savedBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find branch by ID="+branchId));

        updatedBranch.setId(branchId);
        updatedBranch.setCompany(savedBranch.getCompany());
        updatedBranch.setUuid(savedBranch.getUuid());
        updatedBranch.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

        return branchRepository.save(updatedBranch);
    }

    public void connectEmployeeToBranch(Long branchId, BranchEmployeeDto branchEmployeeDto) throws ZapicitoException {
        Branch branch = findBranchById(branchId);
        //  Employee employee = employeeService.findEmployeeById(branchEmployeeDto.getEmployeeId());

        if(branchEmployeeDto.isActive()){
           //branch.getEmployees().add(employee);
        }else {
           //branch.getEmployees().remove(employee);
        }

       // updateBranch(branch, id);
    }

    public void connectAllEmployeesToBranch(Long branchId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        Branch branch = findBranchById(branchId);

    }

    public Branch mapToBranch(BranchDto branchDto) {
       return branchMapper.fromDto(branchDto);
    }

    public void connectServiceToBranch(Long branchId, List<Long> services) throws ZapicitoException {
        servicesService.connectServiceToBranch(branchId, services);
    }

    public void connectAllServicesToBranch(Long branchId) throws ZapicitoException {
        servicesService.connectAllServicesToBranch(branchId);
    }

    public List<com.ruso.zapicito.entity.Service> getAllServicesByBranch(Long branchId) throws ZapicitoException {
        return servicesService.getAllServicesByBranch(branchId);
    }
}
