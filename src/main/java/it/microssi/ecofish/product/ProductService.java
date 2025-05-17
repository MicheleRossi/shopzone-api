package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseService;
import it.microssi.ecofish.productimage.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;

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

    @Override
    public void deleteById(Long id) {
        // Cancella anche le immagini associate al prodotto dal file system
        deleteImagesForProduct(id);
        // Rimuovi il prodotto dal database dopo aver gestito le immagini
        super.deleteById(id);
    }

    /**
     * Cancella le immagini associate al prodotto dal file system.
     * @param idProduct L'ID del prodotto.
     */
    public void deleteImagesForProduct(Long idProduct) {
        Product product = repository.findById(idProduct).orElse(null);
        if (product != null && product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                if (image != null && image.getUrl() != null) {
                    File file = new File("uploads/products/" + Paths.get(image.getUrl()).getFileName());
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
