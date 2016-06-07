package hska.iwi.eShopMaster;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.Product;
import hska.iwi.eShopMaster.model.Category;
import hska.iwi.eShopMaster.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by d059314 on 07.06.16.
 */
public class DataHandler {

    RestTemplate restTemplate = new RestTemplate();

    String apiUrl = "http://localhost:8100/";


    public DataHandler() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }});
    }

    public List<Product> getProducts(int categoryId, String text, Double min, Double max) {
        String q = "categoryId=" + categoryId + "&searchValue=" + text + "&searchPriceMin=" + min + "&searchPriceMax=" + max;
        Product[] products = this.restTemplate.getForObject(apiUrl + "product-api/products?" + q, Product[].class);
        return Arrays.asList(products);
    }

    public Product getProductById(int productId) {
        return this.restTemplate.getForObject(apiUrl + "product-api/products/" + productId, Product.class);
    }

    public List<Category> getCategories() {
        Category[] categories = this.restTemplate.getForObject(apiUrl + "product-api/categories", Category[].class);
        return Arrays.asList(categories);
    }

    public void deleteProduct(int productid) {
        try {
            this.restTemplate.delete(new URI(apiUrl + "product-api/products/" + productid));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            this.restTemplate.delete(new URI(apiUrl + "product-api/categories/" + categoryId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void createCategory(String name) {
        Category c = new Category(name);
        restTemplate.postForEntity(apiUrl + "product-api/categories" , c, Category.class);
    }

    public ResponseEntity<Product> createProduct(String name, Double price, int categoryId, String details) {
        Product p = new Product(name, price, categoryId, details);
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(apiUrl + "product-api/products" , p, Product.class);
        return responseEntity;
    }

    public ResponseEntity<User> createUser(String name, String pwd) {
        User u = new User((long)0, name, pwd, "User");
        String url = apiUrl + "user-api/users";
        System.out.println(url);
        ResponseEntity<User> response = restTemplate.postForEntity(url , u, User.class);
        return response;
    }
}
