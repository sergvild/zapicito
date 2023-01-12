package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.entity.base.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends User {

    @OneToMany(mappedBy = "branch")
    @JsonBackReference
    Set<BranchServices> branchServices;

    public Employee(UserDto userDto) {
        super(userDto.getFirstName(), userDto.getLastName(), userDto.getPassword(), userDto.getPhone(), userDto.getEmail());
    }
}
