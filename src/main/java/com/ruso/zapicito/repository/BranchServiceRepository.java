package com.ruso.zapicito.repository;

import com.ruso.zapicito.entity.Branch;
import com.ruso.zapicito.entity.BranchServices;
import com.ruso.zapicito.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BranchServiceRepository extends JpaRepository<BranchServices, Long> {

    Optional<BranchServices> findByBranchAndService(Branch branch, Service service);

    @Query("select bs from BranchServices bs where bs.branch.id = :branchId and bs.service.id = :serviceId")
    Optional<BranchServices> findByBranchIdAndServiceId(Long branchId, Long serviceId);

    @Query("select bs from BranchServices bs where bs.branch.id = :branchId")
    Set<BranchServices> findAllByBranch(Long branchId);

    @Query("update BranchServices bs set bs.enabled = :enabled WHERE bs.branch.id = :branchId AND bs.service.id = :serviceId")
    void connectServiceToBranch(@Param("branchId") Long branchId, @Param("serviceId") Long serviceId, @Param("enabled") boolean enabled);

    @Transactional
    @Modifying
    @Query("update BranchServices bs set bs.enabled = :enabled WHERE bs.branch.id = :branchId")
    void connectAllServicesToBranch(@Param("branchId") Long branchId, @Param("enabled") boolean enabled);


}
