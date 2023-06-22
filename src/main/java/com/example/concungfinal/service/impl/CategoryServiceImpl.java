package com.example.concungfinal.service.impl;

import com.example.concungfinal.dto.CategoryDto;
import com.example.concungfinal.entity.Category;
import com.example.concungfinal.repository.CategoryRepository;
import com.example.concungfinal.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Iterable<CategoryDto> findAll() {
        Iterable<Category> categories = categoryRepository.findByActivated(true);
        return StreamSupport.stream(categories.spliterator(), true)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(c -> modelMapper.map(c, CategoryDto.class));
    }


    @Override
    public void save(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.setActivated(false);
        categoryRepository.save(category);
    }
}
