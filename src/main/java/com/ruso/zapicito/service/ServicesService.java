package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.dto.EmployeeBranchServiceDto;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.*;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.repository.BranchServiceRepository;
import com.ruso.zapicito.repository.ServiceCategoryRepository;
import com.ruso.zapicito.repository.ServiceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServicesService {

    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final BranchServiceRepository branchServiceRepository;

    public ServicesService(BranchService branchService, EmployeeService employeeService, ServiceRepository serviceRepository, ServiceCategoryRepository serviceCategoryRepository, BranchServiceRepository branchServiceRepository) {
        this.branchService = branchService;
        this.employeeService = employeeService;
        this.serviceRepository = serviceRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.branchServiceRepository = branchServiceRepository;
    }

    public Service createService(Service service, String categoryName) throws ZapicitoException {
        ServiceCategory serviceCategory = getOrCreateServiceCategory(categoryName);

        service.setCategory(serviceCategory);
        return serviceRepository.save(service);
    }

    public ServiceCategory createServiceCategory(ServiceCategory serviceCategory) throws ZapicitoException {
        return serviceCategoryRepository.save(serviceCategory);
    }

    public Service findServiceById(Long id) throws ZapicitoException {
        return serviceRepository.findById(id).orElseThrow(()->new ZapicitoException("Couldn't find service by ID="+id));
    }

    public List<Service> findAllServices(){
        return serviceRepository.findAll();
    }

    public List<ServiceCategory> findAllCategories(){
        return serviceCategoryRepository.findAll();
    }

    public Service updateService(ServiceDto updatedService, Long id) throws ZapicitoException {

        return serviceRepository.findById(id)
                .map(service -> {
                    service.setName(updatedService.getName());
                    service.setDescription(updatedService.getDescription());
                    service.setDuration(updatedService.getDuration());
                    service.setPrice(updatedService.getPrice());
                    service.setCategory(getOrCreateServiceCategory(updatedService.getCategory()));
                    return serviceRepository.save(service);
                })
                .orElseThrow(()->new ZapicitoException("Couldn't find service by ID="+id));

    }

    public void deleteService(Long id){
        serviceRepository.deleteById(id);
    }

    private ServiceCategory getOrCreateServiceCategory(String categoryName){
        Optional<ServiceCategory> category = serviceCategoryRepository
                .findByName(categoryName)
                .stream().findFirst();

        return category.orElseGet(() -> serviceCategoryRepository.save(new ServiceCategory(categoryName)));
    }

    public void connectServiceToBranch(Long branchId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        Branch branch = branchService.findBranchById(branchId);
        Service service = findServiceById(branchServiceDto.getServiceId());

        if(branchServiceDto.isActive()){
            BranchServices branchServices = new BranchServices(branch, service);
            branchServiceRepository.save(branchServices);
        }else {
            BranchServices branchServices = branchServiceRepository.findByBranchAndService(branch, service).orElseThrow(()->new ZapicitoException("Couldn't find"));
            branchServiceRepository.delete(branchServices);
        }

    }

    public void connectAllServicesToBranch(Long branchId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        if (branchServiceDto.isActive()) {
            List<Service> services = serviceRepository.findAll();
            Branch branch = branchService.findBranchById(branchId);
            Set<BranchServices> branchServices = services.stream()
                    .map(service -> new BranchServices(branch, service)).collect(Collectors.toSet());

            Set<BranchServices> branchServicesSet = branch.getBranchServices();
            branchServicesSet.addAll(branchServices);
            branchServiceRepository.saveAll(branchServicesSet);
        }
        else {
            branchServiceRepository.deleteAll();
        }
    }

    public void connectServiceToEmployee(Long employeeId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        BranchServices branchServices = branchServiceRepository
                .findByBranchIdAndServiceId(branchServiceDto.getBranchId(), branchServiceDto.getServiceId())
                .orElseThrow(()->new ZapicitoException("Couldn't find service by ID="+branchServiceDto.getServiceId()));

        if (branchServiceDto.isActive())
            employee.getBranchServices().add(branchServices);
        else
            employee.getBranchServices().remove(branchServices);

        employeeService.updateEmployee(employee);
    }

    public void connectAllServicesToEmployee(Long employeeId, BranchServiceDto branchServiceDto) throws ZapicitoException {
        Employee employee = employeeService.findEmployeeById(employeeId);

        if (branchServiceDto.isActive()) {
            Set<BranchServices> branchServicesList = branchServiceRepository
                    .findAllByBranch(branchServiceDto.getBranchId());
            employee.getBranchServices().addAll(branchServicesList);
        }
        else {
            employee.getBranchServices().clear();
        }

        employeeService.updateEmployee(employee);
    }

}
