package com.hardik.bheem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.bheem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Boolean existsByEmailId(String emailId);

}
