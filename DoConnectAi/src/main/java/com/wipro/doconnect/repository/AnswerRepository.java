package com.wipro.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

	List<Answer> findByQuestion(Question question);

}
