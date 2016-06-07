package hska.iwi.eShopMaster;

import hska.iwi.eShopMaster.model.Product;
import hska.iwi.eShopMaster.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by d059314 on 07.06.16.
 */
public class DataHandler {

    RestTemplate restTemplate = new RestTemplate();

    public DataHandler() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }});
    }

    public List<Product> getProducts(int categoryId, String text, Double min, Double max) {
        String q = "categoryId=" + categoryId + "&searchValue=" + text + "&searchPriceMin=" + min + "&searchPriceMax=" + max;
        Product[] products = this.restTemplate.getForObject("http://localhost:8081/product-api/products?" + q, Product[].class);
        return Arrays.asList(products);
    }

    public Product getProductById(int productId) {
        return this.restTemplate.getForObject("http://localhost:8081/product-api/products/" + productId, Product.class);
    }

    public List<Category> getCategories() {
        Category[] categories = this.restTemplate.getForObject("http://localhost:8081/product-api/categories", Category[].class);
        return Arrays.asList(categories);
    }

}
