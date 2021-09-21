package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

	
	// find the resume by name
	Optional<Resume> findByName(String name);
	
	
	
	// find all names of the resume files
	@Query("select r.name from Resume r")
	List<String> findAllAsName();

}
