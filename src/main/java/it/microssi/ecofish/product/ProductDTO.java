package it.microssi.ecofish.product;

import it.microssi.ecofish.productimage.ProductImageDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "{product.title.notblank}")
    @Size(min = 2, max = 100, message = "{product.title.size}")
    private String title;

    @NotBlank(message = "{product.description.notblank}")
    @Size(max = 1000, message = "{product.description.size}")
    private String description;

    @NotNull(message = "{product.price.notnull}")
    @Positive(message = "{product.price.positive}")
    private Float price;

    @NotBlank(message = "{product.category.notblank}")
    private String category;

    @NotNull(message = "{product.stockQuantity.notnull}")
    @Positive(message = "{product.stockQuantity.positive}")
    private Integer stockQuantity;

    private List<ProductImageDTO> images;

}