package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import java.net.URI;
import java.net.URISyntaxException;
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

    private void addCategoryToProduct(Product p) {
        Category c = this.getCategory(p.getCategory());
        p.setCategoryName(c.getName());
    }



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

        return products;
    }

    public Product getProduct(int productId) {
        Product product = restTemplate.getForObject("http://product-service/products/" + productId, Product.class);
        this.addCategoryToProduct(product);
        return product;
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
