package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableCircuitBreaker
public class ProductProxyController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getProducts() {

        return new ResponseEntity<Iterable<Product>>(productClient.getProducts(), HttpStatus.OK);

    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> getCategories() {
        return new ResponseEntity<Iterable<Category>>(productClient.getCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable int categoryId) {
        return new ResponseEntity<Category>(productClient.getCategory(categoryId), HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<Iterable<User>>(userClient.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userClient.getUser(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) {

        ResponseEntity e = userClient.login(user);
        return e;

        /*Boolean success = userClient.login(user);

        if (success) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }*/

}
