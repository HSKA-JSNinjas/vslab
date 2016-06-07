package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getProducts(
            @RequestParam(defaultValue = "0", name = "categoryId", required = false) int categoryId,
            @RequestParam(defaultValue = "", name = "searchValue", required = false) String text,
            @RequestParam(defaultValue = "0.0", name = "searchPriceMin", required = false) double searchPriceMin,
            @RequestParam(defaultValue = "0.00", name = "searchPriceMax", required = false) double searchPriceMax
    ) {

        Iterable<Product> products;

        if (categoryId != 0) {
            products = repo.findByCategoryId(categoryId);
        } else {
            if (text != "" || searchPriceMax != 0.0 || searchPriceMin != 0.00) {
                if (searchPriceMax == 0.00) {
                    searchPriceMax = 9999999.99;
                }
                products = repo.findByNameContainingAndPriceBetween(text, searchPriceMin, searchPriceMax);
            } else {
                products = repo.findAll();
            }
        }
        return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> getUser(@PathVariable int productId) {
        List<Product> products = repo.findById(productId);

        if (products.size() > 0) {
            return new ResponseEntity<>(products.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int productId) {
        List<Product> products = repo.findById(productId);
        if (products.size() > 0) {
            Product p = products.get(0);
            repo.delete(p);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody Product product) {

        Product toBeSaved = new Product(product.getName(), product.getPrice(), product.getCategory(), product.getDetails());

        repo.save(toBeSaved);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
