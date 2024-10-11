package com.system_stats.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system_stats.service.SystemStats;

@RestController
@CrossOrigin
public class SystemStatsController {
	
	@Autowired
	private SystemStats systemStats;
	
	@GetMapping("/stats")
	public ResponseEntity<Map<String, Object>> getSystemStat(){
		return systemStats.getSystemStats();
	}
}
