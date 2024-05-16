package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Want;

public interface WantRepository extends JpaRepository<Want, Integer> {

	List<Want> findByUserId(Integer userId);
}
