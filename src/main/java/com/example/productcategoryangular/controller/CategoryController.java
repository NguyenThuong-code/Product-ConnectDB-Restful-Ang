package com.example.productcategoryangular.controller;

import com.example.productcategoryangular.model.Category;
import com.example.productcategoryangular.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/list")
    public ResponseEntity<Iterable<Category>> findAllCategories() {
        Iterable<Category> categories = categoryService.findAll();
        if (!categories.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PutMapping("/edit/{id}")
    public  ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> categoryOptional=categoryService.findById(id);
        if (!categoryOptional.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category.setId(id);
        categoryService.save(category);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        Optional<Category> categoryOptional=categoryService.findById(id);
        if(!categoryOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(id);
        return new ResponseEntity<>(categoryOptional.get(),HttpStatus.NO_CONTENT);
    }
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category),HttpStatus.CREATED);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Optional<Category> categoryOptional=categoryService.findById(id);
        return categoryOptional.map(category -> new ResponseEntity<>(category,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
