package com.beat.back.pojo;

import java.util.List;
import java.util.Map;


	
	public class Result {
	    private List<String> rankings; // List of users in ranked order
	    private Map<String, Integer> scores; // User -> Score mapping
	    
	    
		public Result(List<String> rankings, Map<String, Integer> scores) {
			super();
			this.rankings = rankings;
			this.scores = scores;
		}
		
		public List<String> getRankings() {
			return rankings;
		}
		public void setRankings(List<String> rankings) {
			this.rankings = rankings;
		}
		public Map<String, Integer> getScores() {
			return scores;
		}
		public void setScores(Map<String, Integer> scores) {
			this.scores = scores;
		}
	    
	    
	    
	   
	}


