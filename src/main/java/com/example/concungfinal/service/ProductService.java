package com.example.concungfinal.service;

import com.example.concungfinal.dto.CategoryDto;
import com.example.concungfinal.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService extends GeneralService<ProductDto>{
    Iterable<ProductDto> findByNameContaining(String keyword);
    Iterable<ProductDto> findByCategory(CategoryDto categoryDto);
    Page<ProductDto> findAllByFullNameContaining(String fullName, Pageable pageable);
    Page<ProductDto> findAll(Pageable pageable);
    void deleteByCategory(CategoryDto categoryDto);
}
