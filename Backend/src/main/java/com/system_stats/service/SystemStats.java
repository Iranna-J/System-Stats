package com.system_stats.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface SystemStats {
	public ResponseEntity<Map<String, Object>> getSystemStats();
}
