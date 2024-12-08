package com.springBoot.JobSpot.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.springBoot.JobSpot.backend.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByLocationAndExperienceAndRequiredSkillsContaining(
            String location, int experience, String skill);

    List<Job> findByProviderId(String providerId);

	
//	List<Job> findByProviderEmail(String email);
}
