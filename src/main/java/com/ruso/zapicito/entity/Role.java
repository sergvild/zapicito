package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.entity.base.Authority;
import com.ruso.zapicito.entity.base.BaseEntity;
import com.ruso.zapicito.dto.RoleType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    public Role(RoleType name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    private RoleType name;

    @JsonManagedReference
    @ManyToMany
    private Set<Authority> authorities;

}
