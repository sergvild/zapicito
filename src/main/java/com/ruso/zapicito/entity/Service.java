package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service")
public class Service extends BaseEntity {

    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal priceDiscount;
    private boolean isHotPrice;
    private Integer duration;
    private Integer pause;
    private boolean isPause;
    private Integer orderColumn;
    private boolean isDurationHidden;
    private boolean isPriceHidden;
    private boolean live;
    private boolean liveInWidget;
    private boolean isTop;
    private boolean isAutoAllocated;
    private boolean startingAt;
    private boolean isCollective;
    private Integer expenses;
    private String color;
    private Integer priceAmount;
    private String formattedPrice;
    private String priceDiscountFormatted;

    @ElementCollection
    @CollectionTable(name = "service_image", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "image")
    private List<String> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    @JsonIgnore
    @ManyToMany(mappedBy = "services")
    private List<Employee> employees;

    public Integer getFullDuration(){
        return this.duration + this.pause;
    }

}