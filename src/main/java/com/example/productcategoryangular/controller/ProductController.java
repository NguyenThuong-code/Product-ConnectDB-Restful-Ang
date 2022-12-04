package com.example.productcategoryangular.controller;

import com.example.productcategoryangular.model.Product;
import com.example.productcategoryangular.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping("/list")
    public ResponseEntity<Iterable<Product>> findAllProduct(){
        Iterable<Product> products=productService.findAll();
        if (!products.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(products,HttpStatus.OK);
    }
    @PostMapping("findById/{id}")
    public  ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> productOptional= productService.findById(id);
        return productOptional.map(product -> new ResponseEntity<>(product,HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/create")
    public  ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product){
        Optional<Product> productOptional=productService.findById(id);
        if(!productOptional.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(id);
        productService.save(product);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional=productService.findById(id);
        if(!productOptional.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(id);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.NO_CONTENT);
    }
}
