package com.springBoot.JobSpot.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.springBoot.JobSpot.backend.model.JobProvider;

public interface JobProviderRepository extends MongoRepository<JobProvider, String> {
    JobProvider findByEmail(String email);
    public JobProvider findByProviderId(String providerId);

}
