package com.example.concungfinal.service.impl;

import com.example.concungfinal.dto.CategoryDto;
import com.example.concungfinal.dto.ProductDto;
import com.example.concungfinal.entity.Category;
import com.example.concungfinal.entity.Product;
import com.example.concungfinal.repository.ProductRepository;
import com.example.concungfinal.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public Iterable<ProductDto> findAll() {
        Iterable<Product> products = productRepository.findByActivated(true);
        return StreamSupport.stream(products.spliterator(), true)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        Product product = productRepository.findById(id).get();
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return Optional.ofNullable(productDto);
    }


    @Override
    public void save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findById(id).get();
        product.setActivated(false);;
        productRepository.save(product);
    }

    @Override
    public Iterable<ProductDto> findByNameContaining(String keyword) {
        Iterable<Product> products = productRepository.findByNameContaining(keyword);
        return StreamSupport.stream(products.spliterator(), true)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<ProductDto> findByCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Iterable<Product> products = productRepository.findByCategory(category);
        return StreamSupport.stream(products.spliterator(), true)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> findAllByFullNameContaining(String fullName, Pageable pageable) {
        Page<Product> entities = productRepository.findAllByNameContaining(fullName, pageable);
        Page<ProductDto> productDtos = entities.map(entity -> {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(entity, productDto);
            return productDto;
        });
        return productDtos;
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> entities = productRepository.findAll(pageable);
        Page<ProductDto> productDtos = entities.map(entity -> {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(entity, productDto);
            return productDto;
        });
        return productDtos;
    }

    @Override
    public void deleteByCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Iterable<Product> products = productRepository.findByCategory(category);
        for(Product p: products) {
            p.setActivated(false);
        }
    }
}
