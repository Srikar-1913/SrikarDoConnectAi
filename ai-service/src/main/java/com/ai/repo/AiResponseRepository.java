package com.ai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.entity.AiResponse;

public interface AiResponseRepository extends JpaRepository<AiResponse, Integer> {

	List<AiResponse> findByKeywordContainingIgnoreCase(String keyword);
}
