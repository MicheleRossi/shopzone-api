package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService extends BaseService<ProductDTO, Product, Long> {

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (existsById(id)) {
            productDTO.setId(id);
            return save(productDTO);
        }
        return null;
    }
}
