package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.BranchService;
import com.ruso.zapicito.service.ServicesService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;
    private final ServicesService servicesService;

    public BranchController(BranchService branchService, ServicesService servicesService) {
        this.branchService = branchService;
        this.servicesService = servicesService;
    }

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody BranchDto branchDto) throws ZapicitoException {
        Branch branch = new Branch(branchDto);
        return new ResponseEntity<>(branchService.createBranch(branch, branchDto.getCompanyId()), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Branch> findBranch(@RequestParam @ApiParam(name = "id", value = "Branch id", example = "1") Long id) throws ZapicitoException {
        Branch branch = branchService.findBranchById(id);
        return new ResponseEntity<>(branch, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Branch>> findAllBranches(){
        List<Branch> branches = branchService.findAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@RequestBody BranchDto updatedBranch, @PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id) throws ZapicitoException {
        Branch branch = branchService.updateBranch(updatedBranch, id);
        return new ResponseEntity<>(branch, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id){
        branchService.deleteBranch(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services/connect")
    public ResponseEntity<?> connectServiceToBranch(@PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id, @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        servicesService.connectServiceToBranch(id, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/services/connect-all")
    public ResponseEntity<?> connectAllServicesToBranch(@PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id, @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        servicesService.connectAllServicesToBranch(id, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/employees/connect")
    public ResponseEntity<?> connectEmployeeToBranch(@PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id, @RequestBody BranchEmployeeDto branchEmployeeDto) throws ZapicitoException {
        branchService.connectEmployeeToBranch(id, branchEmployeeDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/employees/connect-all")
    public ResponseEntity<?> connectAllEmployeesToBranch(@PathVariable @ApiParam(name = "id", value = "Branch id", example = "1") Long id, @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        branchService.connectAllEmployeesToBranch(id, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
