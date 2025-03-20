package com.ruso.zapicito.controller;

import com.ruso.zapicito.dto.ApiResponse;
import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.dto.BranchEmployeeDto;
import com.ruso.zapicito.dto.BranchServiceDto;
import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.Service;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.service.BranchService;
import com.ruso.zapicito.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(@Lazy BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<Branch>> createBranch(@PathVariable Long companyId,
                                                            @RequestBody BranchDto branchDto) throws ZapicitoException {
        Branch branch = branchService.mapToBranch(branchDto);
        Branch createdBranch = branchService.createBranch(branch, companyId);

        return new ResponseEntity<>(ResponseUtil.success(createdBranch), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> findBranch(@PathVariable Long id) throws ZapicitoException {
        Branch branch = branchService.findBranchById(id);
        return new ResponseEntity<>(ResponseUtil.success(branch), HttpStatus.ACCEPTED);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<Branch>>> findBranchesByCompanyId(@PathVariable Long companyId) {
        List<Branch> branches = branchService.findBranchesByCompanyId(companyId);
        return new ResponseEntity<>(ResponseUtil.success(branches), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<ApiResponse<Branch>> updateBranch(@RequestBody BranchDto branchDto,
                                                            @PathVariable Long branchId) throws ZapicitoException {
        Branch updatedBranch = branchService.mapToBranch(branchDto);

        Branch branch = branchService.updateBranch(updatedBranch, branchId);
        return new ResponseEntity<>(ResponseUtil.success(branch), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<ApiResponse<String>> deleteBranch(@PathVariable Long branchId){
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(ResponseUtil.success("Branch was deleted"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{branchId}/services")
    public ResponseEntity<ApiResponse<String>> connectServiceToBranch(@PathVariable Long branchId,
                                                         @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        branchService.connectServiceToBranch(branchId, branchServiceDto.getServices());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{branchId}/services/all")
    public ResponseEntity<ApiResponse<String>> connectAllServicesToBranch(@PathVariable Long branchId) throws ZapicitoException {
        branchService.connectAllServicesToBranch(branchId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{branchId}/services")
    public ResponseEntity<ApiResponse<List<Service>>> getAllServicesByBranch(@PathVariable Long branchId) throws ZapicitoException {
        return new ResponseEntity<>(ResponseUtil.success(branchService.getAllServicesByBranch(branchId)), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{branchId}/employees/connect")
    public ResponseEntity<ApiResponse<String>> connectEmployeeToBranch(@PathVariable Long branchId,
                                                          @RequestBody BranchEmployeeDto branchEmployeeDto) throws ZapicitoException {
        branchService.connectEmployeeToBranch(branchId, branchEmployeeDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/{branchId}/employees/connect-all")
    public ResponseEntity<ApiResponse<String>> connectAllEmployeesToBranch(@PathVariable Long branchId,
                                                              @RequestBody BranchServiceDto branchServiceDto) throws ZapicitoException {
        branchService.connectAllEmployeesToBranch(branchId, branchServiceDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
