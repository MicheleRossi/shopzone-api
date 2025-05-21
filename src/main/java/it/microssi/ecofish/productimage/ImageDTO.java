package it.microssi.ecofish.productimage;

import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String fileName;
    private String url;
    private boolean mainImage;
}