/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for Answer entity
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;

// Repository interface for Answer table
public interface AnswerRepository extends JpaRepository<Answer, Long>{

    // Get all answers for a specific question
    List<Answer> findByQuestion(Question question);
}