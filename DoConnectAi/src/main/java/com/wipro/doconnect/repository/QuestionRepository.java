package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
