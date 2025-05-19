package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Integer>{

}
