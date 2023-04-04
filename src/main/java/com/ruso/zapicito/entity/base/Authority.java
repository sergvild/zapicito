package com.ruso.zapicito.entity.base;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.entity.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {
    public static final String DEFAULT_ROLE_AUTHORITY = "READ";

    @Column(name = "authority", nullable = true)
    private String authority;
}
