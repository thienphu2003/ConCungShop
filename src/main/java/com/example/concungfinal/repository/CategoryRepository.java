package com.example.concungfinal.repository;

import com.example.concungfinal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Iterable<Category> findByActivated(Boolean isActivated);
}
