package com.cimb.twentyone.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.twentyone.Entity.Characters;

public interface CharactersRepo extends JpaRepository <Characters, Integer> {

}
