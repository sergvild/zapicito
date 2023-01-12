package com.ruso.zapicito.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    @JsonIgnore
    private Timestamp createdDate;

    @Column(name = "updated_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)")
    @JsonIgnore
    private Timestamp updatedDate;

    @Column(name = "enabled", insertable = false, updatable = false, columnDefinition = "BIT(1) NOT NULL DEFAULT 1")
    @JsonIgnore
    private boolean enabled;

}
