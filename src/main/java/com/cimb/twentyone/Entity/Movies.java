package com.cimb.twentyone.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String moviesName;
	private int moviesYear;
	private String moviesDesc;
	
	// Dalam satu tabel gabisa pake 2 eager, harus beda. soalnya kalau sama bakalan error pas dirun
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "movies_category", joinColumns = @JoinColumn(name = "movies_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Categories> categories;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movies", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Characters> characters;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMoviesName() {
		return moviesName;
	}
	public void setMoviesName(String moviesName) {
		this.moviesName = moviesName;
	}
	public int getMoviesYear() {
		return moviesYear;
	}
	public void setMoviesYear(int moviesYear) {
		this.moviesYear = moviesYear;
	}
	public String getMoviesDesc() {
		return moviesDesc;
	}
	public void setMoviesDesc(String moviesDesc) {
		this.moviesDesc = moviesDesc;
	}
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	
}
