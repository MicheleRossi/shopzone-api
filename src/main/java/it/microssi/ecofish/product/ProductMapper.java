package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseMapper;
import it.microssi.ecofish.productimage.ProductImageDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper extends BaseMapper<ProductDTO, Product> {

    @Override
    public ProductDTO mapToDto(Product source) {
        if (source == null) {
            return null;
        }
        
        ProductDTO dto = new ProductDTO();
        dto.setId(source.getId());
        dto.setTitle(source.getTitle());
        dto.setDescription(source.getDescription());
        dto.setPrice(source.getPrice());
        dto.setCategory(source.getCategory());
        dto.setStockQuantity(source.getStockQuantity());
        List<ProductImageDTO> imageDTOs = source.getImages().stream()
                .map(img -> {
                    ProductImageDTO productImageDTO = new ProductImageDTO();
                    productImageDTO.setId(img.getId());
                    productImageDTO.setUrl(img.getUrl());
                    return productImageDTO;
                })
                .collect(Collectors.toList());

        dto.setImages(imageDTOs);
        
        return dto;
    }

    @Override
    public Product mapToEntity(ProductDTO source) {
        if (source == null) {
            return null;
        }
        
        Product entity = new Product();
        entity.setId(source.getId());
        entity.setTitle(source.getTitle());
        entity.setDescription(source.getDescription());
        entity.setPrice(source.getPrice());
        entity.setCategory(source.getCategory());
        entity.setStockQuantity(source.getStockQuantity());
        
        return entity;
    }
}