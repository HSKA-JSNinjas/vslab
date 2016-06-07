package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    public List<Category> findById(int id);

}
