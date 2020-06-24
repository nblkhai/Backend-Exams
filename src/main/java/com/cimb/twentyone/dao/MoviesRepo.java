package com.cimb.twentyone.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.twentyone.Entity.Movies;

public interface MoviesRepo extends JpaRepository<Movies, Integer> {

}
