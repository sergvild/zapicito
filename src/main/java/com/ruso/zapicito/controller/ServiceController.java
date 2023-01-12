package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ServiceCategoryDto;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.entity.ServiceCategory;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.ServicesService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServicesService servicesService;

    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody ServiceDto serviceDto) throws ZapicitoException {
        Service service = new Service(serviceDto);
        return new ResponseEntity<>(servicesService.createService(service, serviceDto.getCategory()), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Service> findService(@RequestParam @ApiParam(name = "id", value = "Service id", example = "1") Long id) throws ZapicitoException {
        Service service = servicesService.findServiceById(id);
        return new ResponseEntity<>(service, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Service>> findAllServices(){
        List<Service> services = servicesService.findAllServices();
        return new ResponseEntity<>(services, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@RequestBody ServiceDto updatedService, @PathVariable @ApiParam(name = "id", value = "Service id", example = "1") Long id) throws ZapicitoException {
        Service service = servicesService.updateService(updatedService, id);
        return new ResponseEntity<>(service, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable @ApiParam(name = "id", value = "Service id", example = "1") Long id){
        servicesService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/categories")
    public ResponseEntity<ServiceCategory> createCategory(@RequestBody ServiceCategoryDto serviceCategoryDto) throws ZapicitoException {
        ServiceCategory serviceCategory = new ServiceCategory(serviceCategoryDto);
        return new ResponseEntity<>(servicesService.createServiceCategory(serviceCategory), HttpStatus.ACCEPTED);
    }
    @GetMapping("/categories/all")
    public ResponseEntity<List<ServiceCategory>> findAllCategories(){
        List<ServiceCategory> categories = servicesService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.ACCEPTED);
    }

}
