package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableCircuitBreaker
public class ProductProxyController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(defaultValue = "0", name = "categoryId", required = false) int categoryId,
                                                         @RequestParam(defaultValue = "", name = "searchValue", required = false) String text,
                                                         @RequestParam(defaultValue = "0.0", name = "searchPriceMin", required = false) double searchPriceMin,
                                                         @RequestParam(defaultValue = "0.00", name = "searchPriceMax", required = false) double searchPriceMax) {
        return new ResponseEntity<Iterable<Product>>(productClient.getProducts(categoryId, text, searchPriceMin, searchPriceMax), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable int productId) {
        return new ResponseEntity<Product>(productClient.getProduct(productId), HttpStatus.OK);
    }


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> getCategories() {
        return new ResponseEntity<Iterable<Category>>(productClient.getCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable int categoryId) {
        return new ResponseEntity<Category>(productClient.getCategory(categoryId), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable int productId) {
        productClient.deleteProduct(productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCategory(@PathVariable int categoryId) {

        Iterable<Product> products = productClient.getProducts(categoryId, "", 0.00, 0.00);

        for (Product product : products) {
            productClient.deleteProduct(product.getId());
        }

        productClient.deleteCategory(categoryId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity createCategory(@RequestBody Category category) {

        ResponseEntity e = productClient.createCategory(category);
        return e;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody Product product) {
        ResponseEntity e = productClient.createProduct(product);
        return e;
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
