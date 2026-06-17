/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for user data
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.User;

// Repository interface for User entity
public interface UserRepository extends JpaRepository<User, Long>{

    // Find user by email
    User findByEmail(String email);
}