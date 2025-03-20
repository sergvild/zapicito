package com.ruso.zapicito.repository;

import com.ruso.zapicito.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e JOIN e.branches b WHERE b.id = :branchId")
    List<Employee> findEmployeesByBranchId(@Param("branchId") Long branchId);
}
