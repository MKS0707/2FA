package com.axis.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.model.ApiLogging;


public interface ApiLoggingRepository extends JpaRepository<ApiLogging, Long> {

	long deleteByCreatedOnLessThan(Date date);

}
