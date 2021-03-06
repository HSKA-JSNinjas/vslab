package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    public List<Product> findById(int id);
    public List<Product> findByCategoryId(int categoryId);
    public List<Product> findByNameContainingAndPriceBetween(String name, Double min, Double max);

}
