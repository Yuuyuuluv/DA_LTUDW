package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    public List<Product> getProductByName(String str) {
        return productRepo.findProductByTitle(str);
    }
    public List<Product> getAllCake() {
        return productRepo.findAll();
    }
    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }
    public void addCake(Product cake) {
        productRepo.save(cake);
    }
    public void removeCake(Long id) {
        productRepo.deleteById(id);
    }
    public void update(Product newCake) {
        productRepo.save(newCake);
    }
    public Page<Product> paginateProduct(Long category, Pageable page) {
        Specification<Product> spec = Specification.where(withQuantity()).and(withIsPublish());
        if (category != 0) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), category)
            );
        }
        return productRepo.findAll(spec,page);
    }
    public static Specification<Product> withIsPublish() {
        return (root, query, builder) -> builder.equal(root.get("isPublish"), true);
    }
    public static Specification<Product> withQuantity() {
        return (root, query, builder) -> builder.greaterThan(root.get("quantity"), 0);
    }


}
