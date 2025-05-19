package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Host;

public interface HostRepository extends JpaRepository<Host, Integer>{

}
