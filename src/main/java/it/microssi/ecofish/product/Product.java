package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseEntity;
import it.microssi.ecofish.productimage.Image;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true, exclude = "images")
@Entity
@Getter
@Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Float price;
    private String imageUrl;
    private String category;
    private Integer stockQuantity;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
}