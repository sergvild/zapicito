package com.ruso.zapicito.repository;

import com.ruso.zapicito.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("SELECT b FROM Branch b JOIN b.company c WHERE c.id = :companyId")
    List<Branch> findBranchesByCompanyId(@Param("companyId") Long companyId);
}
