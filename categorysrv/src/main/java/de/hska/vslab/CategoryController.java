package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepo repo;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> getCategories() {
        Iterable<Category> allPolls = repo.findAll();
        return new ResponseEntity<Iterable<Category>>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getUser(@PathVariable int categoryId) {
        List<Category> categories = repo.findById(categoryId);

        if (categories.size() > 0) {
            return new ResponseEntity<>(categories.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int categoryId) {
        List<Category> categories = repo.findById(categoryId);
        if (categories.size() > 0) {
            Category p = categories.get(0);
            repo.delete(p);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
