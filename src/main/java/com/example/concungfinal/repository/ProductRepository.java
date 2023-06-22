package com.example.concungfinal.repository;

import com.example.concungfinal.entity.Category;
import com.example.concungfinal.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findByActivated(Boolean isActivated);
    Iterable<Product> findByNameContaining(String keyword);
    Iterable<Product> findByCategory(Category category);
    Page<Product> findAllByNameContaining(String fullName, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
