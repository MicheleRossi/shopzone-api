package it.microssi.ecofish.productimage;

import it.microssi.ecofish.base.BaseEntity;
import it.microssi.ecofish.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, exclude = "product")
@Entity
@Data
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean mainImage = false;
}