package com.springBoot.JobSpot.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.springBoot.JobSpot.backend.model.Application;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByJobId(String jobId);
}


