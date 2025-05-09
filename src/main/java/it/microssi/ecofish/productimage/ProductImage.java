package it.microssi.ecofish.productimage;

import it.microssi.ecofish.base.BaseEntity;
import it.microssi.ecofish.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}