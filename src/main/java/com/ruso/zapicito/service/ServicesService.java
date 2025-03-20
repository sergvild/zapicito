package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.*;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.ServiceMapper;
import com.ruso.zapicito.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServicesService {

    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final CategoryService categoryService;

    public Service createService(Service service,
                                 List<Long> categoryIds,
                                 Long companyId) throws ZapicitoException {

        setServiceCompany(service, companyId);
        setServiceCategories(service, categoryIds);
        return serviceRepository.save(service);
    }

    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    public Service updateService(Service updatedService,
                                 Long serviceId,
                                 List<Long> categoryIds) throws ZapicitoException {

        Service savedService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find service by ID=" + serviceId));

        updatedService.setId(serviceId);
        updatedService.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        updatedService.setCompany(savedService.getCompany());

        setServiceCategories(updatedService, categoryIds);
        return serviceRepository.save(updatedService);
    }

    private void setServiceCategories(Service service, List<Long> categoryIds) {
        List<Category> categories = categoryService.findCategoriesByIds(categoryIds);
        service.setCategories(categories);
    }

    private void setServiceCompany(Service service, Long companyId) throws ZapicitoException {
        Company company = companyService.findCompanyById(companyId);
        service.setCompany(company);
    }

    public Service findServiceById(Long id) throws ZapicitoException {
        return serviceRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find service by ID="+id));
    }

    public List<Service> findAllServicesByCompanyId(Long companyId) {
        return serviceRepository.findAllByCompanyId(companyId);
    }

    public void deleteService(Long id){
        serviceRepository.deleteById(id);
    }

    public void connectServiceToBranch(Long branchId, List<Long> serviceIds) throws ZapicitoException {

        Branch branch = branchService.findBranchById(branchId);
        List<Service> services = serviceRepository.findAllById(serviceIds);

        branch.setServices(services);
        branchService.saveBranch(branch);
    }

    public void connectAllServicesToBranch(Long branchId) throws ZapicitoException {
        Branch branch = branchService.findBranchById(branchId);

        List<Service> services = findAllServicesByCompanyId(branch.getCompany().getId());
        branch.setServices(services);
        branchService.saveBranch(branch);
    }

    public void connectServiceToEmployee(Long employeeId, List<Long> serviceIds) throws ZapicitoException {

        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Service> services = serviceRepository.findAllById(serviceIds);
        employee.setServices(services);
        employeeService.saveEmployee(employee);
    }

    public void connectAllServicesToEmployee(Long employeeId) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        List<Service> services = findAllServicesByCompanyId(employee.getCompany().getId());

        employee.setServices(services);
        employeeService.saveEmployee(employee);
    }

    public Service mapToService(ServiceDto serviceDto) {
        return serviceMapper.fromDto(serviceDto);
    }

    public List<Service> getAllServicesByBranch(Long branchId) throws ZapicitoException {
        Branch branch = branchService.findBranchById(branchId);

        return branch.getServices();
    }

    public List<Service> getAllServicesByEmployee(Long employeeId) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        return employee.getServices();
    }
}
