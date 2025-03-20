package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.ServicesService;
import com.ruso.zapicito.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServicesService servicesService;

    public ServiceController(@Lazy ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @PostMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<Service>> createService(@PathVariable Long companyId,
                                                              @RequestBody ServiceDto serviceDto) throws ZapicitoException {
        Service service = servicesService.mapToService(serviceDto);
        Service createdService = servicesService.createService(service, serviceDto.getCategories(), companyId);

        return new ResponseEntity<>(ResponseUtil.success(createdService), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Service>> findService(@PathVariable Long id) throws ZapicitoException {
        Service service = servicesService.findServiceById(id);
        return new ResponseEntity<>(ResponseUtil.success(service), HttpStatus.ACCEPTED);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<Service>>> findAllServices(@PathVariable Long companyId) {
        List<Service> services = servicesService.findAllServicesByCompanyId(companyId);
        return new ResponseEntity<>(ResponseUtil.success(services), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Service>> updateService(@RequestBody ServiceDto serviceDto,
                                                              @PathVariable @ApiParam(name = "id", value = "Service id", example = "1") Long id) throws ZapicitoException {
        Service updatedService = servicesService.mapToService(serviceDto);

        Service service = servicesService.updateService(updatedService, id, serviceDto.getCategories());
        return new ResponseEntity<>(ResponseUtil.success(service), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteService(@PathVariable @ApiParam(name = "id", value = "Service id", example = "1") Long id) {
        servicesService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
