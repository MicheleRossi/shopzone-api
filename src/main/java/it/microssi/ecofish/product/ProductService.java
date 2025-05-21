package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseService;
import it.microssi.ecofish.productimage.Image;
import it.microssi.ecofish.productimage.ImageDTO;
import it.microssi.ecofish.productimage.ImageService;
import it.microssi.ecofish.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.nio.file.Paths;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService extends BaseService<ProductDTO, Product, Long> {

    private final StorageProperties storageProperties;

    public ProductDTO get(Long id) {
        ProductDTO productDTO = super.get(id);
        productDTO.getImages()
                .sort(Comparator.comparing(ImageDTO::isMainImage).reversed()
                        .thenComparing(ImageDTO::getId));

        return productDTO;
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (existsById(id)) {
            productDTO.setId(id);
            return save(productDTO);
        }
        return null;
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        // Cancella anche le immagini associate al prodotto dal file system
        deleteImagesForProduct(id);
        // Rimuovi il prodotto dal database dopo aver gestito le immagini
        super.delete(id);
    }

    /**
     * Cancella le immagini associate al prodotto dal file system.
     * @param idProduct L'ID del prodotto.
     */
    public void deleteImagesForProduct(Long idProduct) {
        Product product = repository.findById(idProduct).orElse(null);
        if (product != null && product.getImages() != null) {
            for (Image image : product.getImages()) {
                if (image != null && image.getFileName() != null) {
                    File file = new File(storageProperties.getUploadDir() + image.getFileName());
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
