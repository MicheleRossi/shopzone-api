package it.microssi.ecofish.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
    private String uploadDir;
    private List<String> allowedFileTypes;
    
    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;
}
