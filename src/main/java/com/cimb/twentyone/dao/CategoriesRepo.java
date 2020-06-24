package com.cimb.twentyone.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.twentyone.Entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer> {

}
