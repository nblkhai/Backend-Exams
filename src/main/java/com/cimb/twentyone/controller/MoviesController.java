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
public class MoviesController {

	@Autowired
	private MoviesRepo moviesRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@GetMapping("/movies")
	public Iterable<Movies> getMovies(){
		return moviesRepo.findAll();
	}
	
	@PostMapping("/addMovies")
	public Movies addMovies(@RequestBody Movies movies) {
		return moviesRepo.save(movies);
	}
	
	//Edit Movies 
	@PutMapping("/editMovies")
	public Movies editMovies(@RequestBody Movies movies) {
		Optional<Movies> findMovies = moviesRepo.findById(movies.getId());
		
		// kalau pas diedit moviesnya engga ada, bakalan munculin alert itu tp kalau berhasil bakalan ngesave Movies yang ada di requestbody
		if (findMovies.toString() == "Optional.empty")
			throw new RuntimeException("Movie with this id" + movies.getId() + "doesnt exist");
		return moviesRepo.save(movies);
	}
	
	//Delete Movies 
		@DeleteMapping("/movies/{moviesId}")
		public void deleteMovies(@PathVariable int moviesId) {
			Movies findMovies = moviesRepo.findById(moviesId).get();
			findMovies.getCategories().forEach(categories -> {
				List<Movies> categoriesMovies = categories.getMovies();
				categoriesMovies.remove(findMovies);
				categoriesRepo.save(categories);
			});
			findMovies.setCategories(null);
			moviesRepo.deleteById(moviesId);
		}
	// Add Categories to Movies
	@PostMapping("/{moviesId}/categories/{categoriesId}")
	
	public Movies addCategoriesToMovies(@PathVariable int moviesId, @PathVariable int categoriesId) {
		Movies findMovies = moviesRepo.findById(moviesId).get();
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		findMovies.getCategories().add(findCategories);
		return moviesRepo.save(findMovies);
	}
	
	// Delete Movies --> Masih Error, udah bisa didelete tapi di get masih ada --> pake yang kemaren abangnya ajarin bisa 
//	@DeleteMapping("/movies/delete/{id}")
//	public void deleteMoviesById(@PathVariable int id) {
//		Optional<Movies> findMovies = moviesRepo.findById(id);
//		// kalau pas didelete moviesnya engga ada, bakalan muncul alert itu tp kalau berhasil bakalan langsung delete by id
//		if (findMovies.toString() == "Optional.empty")
//			throw new RuntimeException("Movies Not Found!");
//		
//			moviesRepo.deleteById(id);
//	}
	
	// Delete Categories from Movies --> gausah dipake lagi, delete yang pake list udah bisa langsung delete categories jugak 
//	@DeleteMapping("/{moviesId}/categories/{categoriesId}")
//	public Movies deleteMoviesCategory (@PathVariable int moviesId, @PathVariable int categoriesId) {
//		Movies findMovies = moviesRepo.findById(moviesId).get();
//		Categories findCategories = categoriesRepo.findById(categoriesId).get();
//		findMovies.getCategories().remove(findCategories);
//		return moviesRepo.save(findMovies);
//	}
	

}
