package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    public List<User> findByNameAndPasswd(String name, String passwd);
    public List<User> findByName(String name);

}
