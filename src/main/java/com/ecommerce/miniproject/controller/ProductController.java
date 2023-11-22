package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.ProductDTO;
import com.ecommerce.miniproject.dto.ResponseObject;
import com.ecommerce.miniproject.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.miniproject.model.Product;

@RestController
@RequestMapping("/${api.path.products}")
public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseObject> getProducts(
            @RequestParam(name = "activeStatus", defaultValue = "Active", required = false) String activeStatus) {
        return sendFoundResponse(productService.getProducts(activeStatus));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createProduct(@RequestBody Product product) {
        return sendCreatedResponse(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable String id) {
        return sendFoundResponse(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProductById(@PathVariable String id, @RequestBody Product product) {
        return sendSuccessResponse(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> removeProduct(@PathVariable String id) {
        return sendSuccessResponse(productService.deactivateProduct(id));
    }
}
