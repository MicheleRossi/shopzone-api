package it.microssi.ecofish.productimage;

import it.microssi.ecofish.base.BaseMapper;
import it.microssi.ecofish.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageMapper extends BaseMapper<ImageDTO, Image> {
    private final StorageProperties storageProperties;

    public ImageDTO mapToDto(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setFileName(image.getFileName());
        dto.setUrl(storageProperties.getBaseUrl() + storageProperties.getUploadDir() + image.getFileName());
        dto.setMainImage(image.isMainImage());
        return dto;
    }

    @Override
    public Image mapToEntity(ImageDTO source) {
        Image entity = new Image();
        entity.setId(source.getId());
        entity.setFileName(source.getFileName());
        entity.setMainImage(source.isMainImage());
        return entity;
    }
}
