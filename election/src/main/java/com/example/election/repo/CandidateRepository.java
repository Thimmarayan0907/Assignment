package com.example.election.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.election.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	
    Candidate findByName(String name);

    
}
