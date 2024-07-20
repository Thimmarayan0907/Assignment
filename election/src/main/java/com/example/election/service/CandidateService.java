package com.example.election.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.election.model.Candidate;
import com.example.election.repo.CandidateRepository;

@Service
@Transactional
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate enterCandidate(String name) {
        Candidate candidate = candidateRepository.findByName(name);
        if (candidate != null) {
            return null; 
        }
        candidate = new Candidate();
        candidate.setName(name);
        candidate.setVoteCount(0);
        return candidateRepository.save(candidate);
    }

    public Candidate castVote(String name) {
        Candidate candidate = candidateRepository.findByName(name);
        if (candidate != null) {
            candidate.setVoteCount(candidate.getVoteCount() + 1);
            return candidateRepository.save(candidate);
        }
        return null; 
    }

    public Integer countVote(String name) {
        Candidate candidate = candidateRepository.findByName(name);
        if (candidate != null) {
            return candidate.getVoteCount();
        }
        return null; 
    }

    public List<Candidate> listVote() {
        return candidateRepository.findAll();
    }

    public Candidate getWinner() {
        List<Candidate> candidates = candidateRepository.findAll();
        if (candidates.isEmpty()) {
            return null; 
        }

        Candidate winner = candidates.get(0);
        for (Candidate candidate : candidates) {
            if (candidate.getVoteCount() > winner.getVoteCount()) {
                winner = candidate;
            }
        }
        return winner;
    }
}
