package com.ruso.zapicito.repository;

import com.ruso.zapicito.dto.EmployeeScheduleDto;
import com.ruso.zapicito.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByEmployeeIdAndBranchId(Long employeeId, Long branchId);


    @Query("SELECT new com.ruso.zapicito.dto.EmployeeScheduleDto(e.id, e.name, s.id) FROM Employee e " +
            "inner join Schedule s on s.employeeId=e.id where s.branchId=:branchId")
    List<EmployeeScheduleDto> findEmployeeScheduleByBranchId(Long branchId);

    @Query(value = "SELECT s from Schedule s where s.branchId=:branchId")
    List<Schedule> findByBranchId(Long branchId);
}
