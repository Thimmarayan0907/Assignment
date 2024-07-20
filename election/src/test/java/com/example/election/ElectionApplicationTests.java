package com.example.election;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.election.controller.BaseController;
import com.example.election.model.Candidate;
import com.example.election.service.CandidateService;

@SpringBootTest
@AutoConfigureMockMvc
class ElectionApplicationTests {

	 @Autowired
	    private MockMvc mockMvc;

	    @Mock
	    private CandidateService candidateService;

	    @InjectMocks
	    private BaseController baseController;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testCastVote_Success() throws Exception {
	        when(candidateService.castVote(anyString())).thenReturn(new Candidate("John"));

	        mockMvc.perform(MockMvcRequestBuilders.post("/castvote")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("name", "John"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("Vote casted for 'John'."));

	        verify(candidateService, times(1)).castVote("John");
	    }

	    @Test
	    public void testCastVote_CandidateNotFound() throws Exception {
	       
	        when(candidateService.castVote(anyString())).thenReturn(null);

	              mockMvc.perform(MockMvcRequestBuilders.post("/castvote")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("name", "John"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("Candidate 'John' not found."));

	        verify(candidateService, times(1)).castVote("John");
	    }

	    @Test
	    public void testCountVote_Success() throws Exception {
	        
	        when(candidateService.countVote(anyString())).thenReturn(10);

	        
	        mockMvc.perform(MockMvcRequestBuilders.get("/countvote")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("name", "John"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("Vote count for 'John' is 10."));

	       
	        verify(candidateService, times(1)).countVote("John");
	    }
	    @Test
	    public void testGetWinner_Success() throws Exception {
	        Candidate winner = new Candidate("John", 20);
	        when(candidateService.getWinner()).thenReturn(winner);

	        mockMvc.perform(MockMvcRequestBuilders.get("/getwinner"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("The winner is 'John' with 20 votes."));

	        verify(candidateService, times(1)).getWinner();
	    }

	    @Test
	    public void testGetWinner_NoCandidatesAvailable() throws Exception {
	        when(candidateService.getWinner()).thenReturn(null);

	        mockMvc.perform(MockMvcRequestBuilders.get("/getwinner"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("No candidates available."));
	        verify(candidateService, times(1)).getWinner();
	    }

}
