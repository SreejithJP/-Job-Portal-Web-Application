package com.springBoot.JobSpot.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springBoot.JobSpot.backend.model.JobProvider;
import com.springBoot.JobSpot.backend.model.JobSeeker;

public interface JobSeekerRepository extends MongoRepository<JobSeeker, String> {
    JobSeeker findByEmail(String email);

	JobSeeker findBySeekerId(String seekerId);
}