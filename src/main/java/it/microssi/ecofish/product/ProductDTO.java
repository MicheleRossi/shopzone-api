package it.microssi.ecofish.product;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Float price;
    private String imageUrl;
    private String category;
    private Integer stockQuantity;
}