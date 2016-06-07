package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class UserClient {

    private final Map<Long, User> userCache = new LinkedHashMap<Long, User>();

    /*
    @Autowired
    private RestTemplate restTemplate;
    */

    @Autowired
    private RestTemplate restTemplate;

    /*RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }});
    }


    @HystrixCommand(fallbackMethod = "getUsersCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Iterable<User> getUsers() {
        System.out.print("HALLO");
        Collection<User> users = new HashSet<User>();
        User[] tmpusers = restTemplate.getForObject("http://user-service/users", User[].class);
        Collections.addAll(users, tmpusers);
        userCache.clear();
        users.forEach(u -> userCache.put(u.getId(), u));
        return users;
    }

    @HystrixCommand(fallbackMethod = "getUserCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public User getUser(Long userId) {
        User tmpuser = restTemplate.getForObject("http://user-service/users/" + userId, User.class);
        userCache.putIfAbsent(userId, tmpuser);
        return tmpuser;
    }

    public ResponseEntity<User> login(User user) {
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://user-service/login" , user, User.class);
        return responseEntity;
    }

    public ResponseEntity<User> createUser(User user) {
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://user-service/users" , user, User.class);
        return responseEntity;
    }

    public Iterable<User> getUsersCache() {
        return userCache.values();
    }

    public User getUserCache(Long userId) {
        return userCache.getOrDefault(userId, new User());
    }

}
