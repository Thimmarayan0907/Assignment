package com.example.election.controller;


import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.election.model.Candidate;
import com.example.election.service.CandidateService;

@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    
	  @Autowired
	    private CandidateService candidateService;

	  @PostMapping("/entercandidate")
	    public String enterCandidate(@RequestParam String name) {
	        try {
	            Candidate candidate = candidateService.enterCandidate(name);
	            if (candidate != null) {
	                return "Candidate '" + name + "' entered successfully.";
	            } else {
	                return "Candidate '" + name + "' already exists.";
	            }
	        } catch (Exception e) {
	            logger.error("Error occurred while processing enterCandidate API: " + e.getMessage(), e);
	            return "An error occurred while entering candidate.";
	        }
	    }
	  @PostMapping("/castvote")
	    public String castVote(@RequestParam String name) {
	        try {
	            Candidate candidate = candidateService.castVote(name);
	            if (candidate != null) {
	                return "Vote casted for '" + name + "'.";
	            } else {
	                return "Candidate '" + name + "' not found.";
	            }
	        } catch (Exception e) {
	            logger.error("Error occurred while casting vote for candidate '" + name + "': " + e.getMessage(), e);
	            return "An error occurred while casting vote.";
	        }
	    }

	    @GetMapping("/countvote")
	    public String countVote(@RequestParam String name) {
	        try {
	            Integer voteCount = candidateService.countVote(name);
	            if (voteCount != null) {
	                return "Vote count for '" + name + "' is " + voteCount + ".";
	            } else {
	                return "Candidate '" + name + "' not found.";
	            }
	        } catch (Exception e) {
	            logger.error("Error occurred while counting votes for candidate '" + name + "': " + e.getMessage(), e);
	            return "An error occurred while counting votes.";
	        }
	    }

	    @GetMapping("/listvote")
	    public List<Candidate> listVote() {
	        try {
	            return candidateService.listVote();
	        } catch (Exception e) {
	            logger.error("Error occurred while retrieving list of votes: " + e.getMessage(), e);
	            // You might return an empty list or handle this case based on your application's logic
	            return Collections.emptyList();
	        }
	    }

	    @GetMapping("/getwinner")
	    public String getWinner() {
	        try {
	            Candidate winner = candidateService.getWinner();
	            if (winner != null) {
	                return "The winner is '" + winner.getName() + "' with " + winner.getVoteCount() + " votes.";
	            } else {
	                return "No candidates available.";
	            }
	        } catch (Exception e) {
	            logger.error("Error occurred while retrieving the winner: " + e.getMessage(), e);
	            return "An error occurred while retrieving the winner.";
	        }
	    }
	}
