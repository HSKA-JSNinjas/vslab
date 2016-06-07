package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import java.util.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    


    public Iterable<Product> getProducts() {
        Collection<Product> products = new HashSet<Product>();
        Product[] tmpusers = restTemplate.getForObject("http://product-service/products", Product[].class);
        Collections.addAll(products, tmpusers);
        return products;
    }

    public Iterable<Product> getProductsForCategoryId(int categoryId) {
        Collection<Product> products = new HashSet<Product>();
        Product[] tmpusers = this.getProductsByCategoryId(categoryId);
        Collections.addAll(products, tmpusers);
        return products;
    }

    public Iterable<Category> getCategories() {
        Collection<Category> categories = new HashSet<Category>();
        Category[] tmpcategories = restTemplate.getForObject("http://category-service/categories", Category[].class);
        Collections.addAll(categories, tmpcategories);
        return categories;
    }

    public Category getCategory(int categoryId) {
        Category category = restTemplate.getForObject("http://category-service/categories/" + categoryId, Category.class);
        Product[] products = this.getProductsByCategoryId(categoryId);
        category.setProducts(new HashSet<Product>(Arrays.asList(products)));
        return category;
    }


}
