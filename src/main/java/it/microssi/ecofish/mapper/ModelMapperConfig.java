package it.microssi.ecofish.mapper;

import it.microssi.ecofish.base.BaseMapper;
import it.microssi.ecofish.product.Product;
import it.microssi.ecofish.product.ProductDTO;
import it.microssi.ecofish.user.User;
import it.microssi.ecofish.user.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BaseMapper<ProductDTO, Product> productMapper(ModelMapper modelMapper) {
        return new BaseMapper<>(modelMapper, ProductDTO.class, Product.class);
    }

    @Bean
    public BaseMapper<UserDTO, User> userMapper(ModelMapper modelMapper) {
        return new BaseMapper<>(modelMapper, UserDTO.class, User.class);
    }
}
