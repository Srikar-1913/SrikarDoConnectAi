/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for questions
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Question;

// Repository interface for Question entity
public interface QuestionRepository extends JpaRepository<Question, Long>{

}