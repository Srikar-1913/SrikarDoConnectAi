package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Impression;
import com.wipro.doconnect.entity.ImpressionType;

public interface ImpressionRepository extends JpaRepository<Impression, Long> {

	Long countByAnswer_AnswerIdAndType(Long answerId, ImpressionType type);

	void deleteByAnswer(Answer answer);
	
}