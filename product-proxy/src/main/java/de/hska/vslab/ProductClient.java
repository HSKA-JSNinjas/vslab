package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Component
public class ProductClient {

    @Autowired
    private RestTemplate restTemplate;

    private final Map<Integer, Product> productCache = new LinkedHashMap<>();
    private final Map<Integer, Category> categoryCache = new LinkedHashMap<>();


    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }});
    }

    private Product[] getProductsByCategoryId(int categoryId) {
        return restTemplate.getForObject("http://product-service/products?categoryId=" + categoryId, Product[].class);
    }

    private void addCategoryToProduct(Product p) {
        Category c = this.getCategory(p.getCategory());
        p.setCategoryName(c.getName());
    }


    @HystrixCommand(fallbackMethod = "getCachedProducts", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Iterable<Product> getProducts(int categoryId, String text, Double min, Double max) {
        Collection<Product> products = new HashSet<Product>();
        String q = "categoryId=" + categoryId + "&searchValue=" + text + "&searchPriceMin=" + min + "&searchPriceMax=" + max;
        System.out.println("Query: " + q);
        Product[] tmpproducts = restTemplate.getForObject("http://product-service/products?" + q, Product[].class);
        for (int i = 0; i < tmpproducts.length; i++) {
            Product p = tmpproducts[i];
            addCategoryToProduct(p);
        }
        Collections.addAll(products, tmpproducts);
        productCache.clear();
        products.forEach(p -> productCache.put(p.getId(), p));
        return products;
    }

    public Iterable<Product> getCachedProducts(int categoryId, String text, Double min, Double max){
        //should be filtered for real service
        return productCache.values();
    }

    @HystrixCommand(fallbackMethod = "getCachedProduct", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Product getProduct(int productId) {
        Product product = restTemplate.getForObject("http://product-service/products/" + productId, Product.class);
        this.addCategoryToProduct(product);
        return product;
    }

    public Product getCachedProduct(int productId){
        return productCache.getOrDefault(productId, new Product());
    }

    public void deleteProduct(int productId) {
        try {
            restTemplate.delete(new URI("http://product-service/products/" + productId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            restTemplate.delete(new URI("http://category-service/categories/" + categoryId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Iterable<Product> getProductsForCategoryId(int categoryId) {
        Collection<Product> products = new HashSet<Product>();
        Product[] tmpusers = this.getProductsByCategoryId(categoryId);
        Collections.addAll(products, tmpusers);
        return products;
    }

    @HystrixCommand(fallbackMethod = "getCachedCategories", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Iterable<Category> getCategories() {
        Collection<Category> categories = new HashSet<Category>();
        Category[] tmpcategories = restTemplate.getForObject("http://category-service/categories", Category[].class);
        Collections.addAll(categories, tmpcategories);
        categoryCache.clear();
        categories.forEach(c -> categoryCache.put(c.getId(), c));
        return categories;
    }

    public Iterable<Category> getCachedCategories() {
        return categoryCache.values();
    }

    @HystrixCommand(fallbackMethod = "getCachedCategory", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Category getCategory(int categoryId) {
        Category category = restTemplate.getForObject("http://category-service/categories/" + categoryId, Category.class);
        Product[] products = this.getProductsByCategoryId(categoryId);
        category.setProducts(new HashSet<Product>(Arrays.asList(products)));
        return category;
    }

    public Category getCachedCategory(int categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

    public ResponseEntity<Category> createCategory(Category category) {
        ResponseEntity<Category> responseEntity = restTemplate.postForEntity("http://category-service/categories" , category, Category.class);
        return responseEntity;
    }

    public ResponseEntity<Product> createProduct(Product product) {
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity("http://product-service/products" , product, Product.class);
        return responseEntity;
    }
}
