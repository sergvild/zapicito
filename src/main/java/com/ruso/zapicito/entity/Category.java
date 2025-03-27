package com.ruso.zapicito.entity;

import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "category")
public class Category extends BaseEntity {
    private String name;
    private String color;

    @ElementCollection
    @CollectionTable(name = "category_images", joinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "image_url")
    private List<String> images;

}
