package com.cimb.twentyone.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.twentyone.Entity.Characters;
import com.cimb.twentyone.Entity.Movies;
import com.cimb.twentyone.dao.CharactersRepo;
import com.cimb.twentyone.dao.MoviesRepo;

@RestController
public class CharactersController {
	
	@Autowired
	private CharactersRepo charactersRepo;
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@GetMapping("/characters")
	public Iterable<Characters> getCharacters(){
		return charactersRepo.findAll();
	}
	
	//Edit Characters --> Error SQL (Movies_id column cannot be null)
//		@PutMapping("/editCharacters")
//		public Characters editCharacters(@RequestBody Characters characters) {
//			Optional<Characters> findCharacters = charactersRepo.findById(characters.getId());
//			
//			// kalau pas diedit charactersnya engga ada, bakalan munculin alert itu tp kalau berhasil bakalan ngesave characters yang ada di requestbody
//			if (findCharacters.toString() == "Optional.empty")
//				throw new RuntimeException("Characters with this id" + characters.getId() + "doesnt exist");
//			return charactersRepo.save(characters);
//		}
	
	// Edit Characters
	@PutMapping("/editCharacters/{idK}")
	public Characters editCharacters(@RequestBody Characters characters, @PathVariable int idK) {
		Characters findKarakter = charactersRepo.findById(idK).get();
//		Optional<Characters> findCharacters = charactersRepo.findById(characters.getId());
		if(findKarakter.toString() == "Optional.empty")
			throw new RuntimeException("Characters with this id" + characters.getId() + "doesnt exist");
//		characters.setMovies(null);
		return charactersRepo.save(findKarakter);
	}
		
	// Add Characters to Movies --> add charactersnya langsung ke movies, gak satu-satu kaya categories
		@PostMapping("/movies/{moviesId}")
		public Characters addCharactersToMovies (@RequestBody Characters characters, @PathVariable int moviesId) {
			Movies findMovies = moviesRepo.findById(moviesId).get();
			
			if (findMovies == null)
				throw new RuntimeException("Movies not found!");
			characters.setMovies(findMovies);
			
			return charactersRepo.save(characters);
		}
		
	// Delete Characters --> yang kehapus harusnya charactersnya doang 
		@DeleteMapping("/characters/delete/{id}")
		public void deleteCharactersById(@PathVariable int id) {
			Optional<Movies> findCharacters = moviesRepo.findById(id);
			// kalau pas didelete characters engga ada, bakalan muncul alert itu tp kalau berhasil bakalan langsung delete characters by id
			if (findCharacters.toString() == "Optional.empty")
				throw new RuntimeException("Characters Not Found!");
			
				charactersRepo.deleteById(id);
		}
}
