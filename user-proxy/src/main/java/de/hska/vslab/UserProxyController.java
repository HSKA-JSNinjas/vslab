package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCircuitBreaker
public class UserProxyController {

    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<Iterable<User>>(userClient.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userClient.getUser(userId), HttpStatus.OK);
    }

}
