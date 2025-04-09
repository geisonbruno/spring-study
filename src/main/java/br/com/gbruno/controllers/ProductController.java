package br.com.gbruno.controllers;

import br.com.gbruno.dto.ProductRecordDto;
import br.com.gbruno.models.ProductModel;
import br.com.gbruno.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    public static final String PRODUCT_NOT_FOUND = "Product not found";

    @Autowired
    ProductRepository productRepository;


    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(PRODUCT_NOT_FOUND);
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(product0.get());
        }

        @PutMapping("/products/{id}")
        public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid ProductRecordDto productRecordDto) {
            Optional<ProductModel> product0 = productRepository.findById(id);
            if (product0.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(PRODUCT_NOT_FOUND);
            }
            var productModel = product0.get();
            BeanUtils.copyProperties(productRecordDto, productModel);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productRepository.save(productModel));
        }

    }