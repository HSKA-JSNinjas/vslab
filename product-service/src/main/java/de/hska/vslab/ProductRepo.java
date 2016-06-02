package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    public List<Product> findByNameAndPasswd(String name, String passwd);

}
