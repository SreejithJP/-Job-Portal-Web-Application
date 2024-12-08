package com.springBoot.JobSpot.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springBoot.JobSpot.backend.model.JobSeeker;
import com.springBoot.JobSpot.backend.model.JobProvider;
import com.springBoot.JobSpot.backend.repository.JobSeekerRepository;
import com.springBoot.JobSpot.backend.repository.JobProviderRepository;

@Service
public class AuthService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobProviderRepository jobProviderRepository;

    public String registerSeeker(JobSeeker jobSeeker) {
        if (jobSeekerRepository.findByEmail(jobSeeker.getEmail()) != null) {
            return "Job Seeker already exists!";
        }
        jobSeekerRepository.save(jobSeeker);
        return "Job Seeker registered successfully!";
    }

    public String registerProvider(JobProvider jobProvider) {
        if (jobProviderRepository.findByEmail(jobProvider.getEmail()) != null) {
            return "Job Provider already exists!";
        }
        jobProviderRepository.save(jobProvider);
        return "Job Provider registered successfully!";
    }

    public String login(String email, String password) {
        JobSeeker seeker = jobSeekerRepository.findByEmail(email);
        if (seeker != null && seeker.getPassword().equals(password)) {
            return "Login successful for Job Seeker!";
        }

        JobProvider provider = jobProviderRepository.findByEmail(email);
        if (provider != null && provider.getPassword().equals(password)) {
            return "Login successful for Job Provider!";
        }

        return "Invalid credentials!";
    }
}
