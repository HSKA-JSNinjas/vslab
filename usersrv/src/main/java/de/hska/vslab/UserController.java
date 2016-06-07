package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsers() {
        Iterable<User> allPolls = repo.findAll();
        return new ResponseEntity<Iterable<User>>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = repo.findOne(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        repo.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> dbUsers = repo.findByNameAndPasswd(user.getName(), user.getPasswd());
        if (dbUsers.size() > 0) {
            return new ResponseEntity<>(dbUsers.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {

        System.out.println("USERS:");
        List<User> dbUsers = repo.findByName(user.getName());
        System.out.println("USERS: " + dbUsers.size());

        if (dbUsers.size() > 0) {
            User u1 = dbUsers.get(0);
            System.out.println("USER: " + u1.getName());
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        } else {
            User u = new User(user.getName(), user.getPasswd(), user.getRole());
            repo.save(u);
            return new ResponseEntity<User>(u, HttpStatus.CREATED);
        }
    }

}
