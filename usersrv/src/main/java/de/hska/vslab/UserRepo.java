package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> { }
