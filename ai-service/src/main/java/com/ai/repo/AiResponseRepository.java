/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for AI response data
 * Created Date: 17-06-2026
 */

package com.ai.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.entity.AiResponse;

// Repository interface for AI response
public interface AiResponseRepository extends JpaRepository<AiResponse, Integer> {

    // Fetch responses where keyword matches (case insensitive)
    List<AiResponse> findByKeywordContainingIgnoreCase(String keyword);
}