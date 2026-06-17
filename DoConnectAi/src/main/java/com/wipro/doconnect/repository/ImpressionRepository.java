/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for impressions
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Impression;
import com.wipro.doconnect.entity.ImpressionType;

// Repository interface for Impression entity
public interface ImpressionRepository extends JpaRepository<Impression, Long> {

    // Count impressions (like/dislike) for a specific answer
    Long countByAnswer_AnswerIdAndType(Long answerId, ImpressionType type);

    // Delete impressions related to a specific answer
    void deleteByAnswer(Answer answer);
}
