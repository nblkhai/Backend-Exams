package com.cimb.twentyone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.twentyone.Entity.Categories;
import com.cimb.twentyone.Entity.Movies;
import com.cimb.twentyone.dao.CategoriesRepo;
import com.cimb.twentyone.dao.MoviesRepo;

@RestController
public class CategoriesController {
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	// Get all categories
	@GetMapping("/categories")
	public Iterable<Categories> getAllCategories(){
		return categoriesRepo.findAll();
}
	
	// Get categories by Id
	@GetMapping("{categoriesId}")
	public Categories getCategoriesById(@PathVariable int categoriesId) {
		return categoriesRepo.findById(categoriesId).get();
		}
	
	// Get Movies in Category 
	@GetMapping("/{categoriesId}/movies")
	public List<Movies> getMoviesCategories (@PathVariable int categoriesId){
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		return findCategories.getMovies();
	}
	
	// Edit Categories
	@PutMapping("/editCategories")
	public Categories editCategories(@RequestBody Categories categories) {
//		Optional<Categories> findCategories = categoriesRepo.findById(categories.getId());
		// kalau pas diedit categoriesnya engga ada, bakalan munculin alert itu tp kalau berhasil bakalan ngesave Categoriesnya yang ada di requestbody
//		if (findCategories.toString() == "Optional.empty")
//			throw new RuntimeException("Movie with this id" + categories.getId() + "doesnt exist");
		Categories findCategories = categoriesRepo.findById(categories.getId()).get();
		categories.setMovies(findCategories.getMovies());
		return categoriesRepo.save(categories);
	}
	
	// Add categories
	@PostMapping("/addCategory")
	public Categories addCategories(@RequestBody Categories categories) {
		return categoriesRepo.save(categories);
	}
	
	// Delete Categories
	@DeleteMapping("{categoriesId}")
	public void deleteCategories (@PathVariable int categoriesId) {
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		findCategories.getMovies().forEach(movies ->{
			List<Categories> movieCategories = movies.getCategories();
			movieCategories.remove(findCategories);
			moviesRepo.save(movies);
		});
		
		findCategories.setMovies(null);
		categoriesRepo.save(findCategories);
		categoriesRepo.deleteById(categoriesId);
	}
	
}