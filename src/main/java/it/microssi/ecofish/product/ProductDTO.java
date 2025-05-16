package it.microssi.ecofish.product;

import it.microssi.ecofish.productimage.ProductImageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Float price;
    private String category;
    private Integer stockQuantity;
    private List<ProductImageDTO> images;
}