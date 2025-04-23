package it.microssi.ecofish.product;

import it.microssi.ecofish.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}