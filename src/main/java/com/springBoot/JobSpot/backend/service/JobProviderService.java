package com.springBoot.JobSpot.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.JobSpot.backend.model.Application;
import com.springBoot.JobSpot.backend.model.Job;
import com.springBoot.JobSpot.backend.model.JobProvider;
import com.springBoot.JobSpot.backend.repository.ApplicationRepository;
import com.springBoot.JobSpot.backend.repository.JobProviderRepository;
import com.springBoot.JobSpot.backend.repository.JobRepository;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JobProviderService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private JobProviderRepository jobProviderRepository;
//    @Autowired
//    private JobProviderService jobProviderService;

    public String registerProvider(JobProvider jobProvider) {
        jobProviderRepository.save(jobProvider);
        return "Job Provider registered successfully!";
    }

    public List<Job> getJobs(String providerId) {
        System.out.println("Fetching jobs for providerId: " + providerId);
        return jobRepository.findByProviderId(providerId);
    }


 // Method to fetch applicants from the Job object
    public List<Application> getApplicantsByJobId(String jobId) {
        System.out.println("Fetching job details for jobId: " + jobId);

        // Fetch the job object by its ID
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        
        // Check if the job object exists
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();  // Get the job object
            
            // Check if there are applicants
            if (job.getApplicants() != null && !job.getApplicants().isEmpty()) {
                return job.getApplicants();  // Return the applicants list
            } else {
                System.out.println("No applicants found for jobId: " + jobId);
                return Collections.emptyList();  // Return an empty list if no applicants
            }
        } else {
            System.out.println("Job not found for jobId: " + jobId);
            return Collections.emptyList();  // Return an empty list if the job doesn't exist
        }
    }
    public String addJob(Job job, HttpSession session) {
        System.out.println("In add jobs..id :" + job.getId());
        System.out.println("In add jobs..company name :" + job.getCompanyName());
        System.out.println("In add jobs..provider id :" + job.getProviderId());

        // Fetch provider details from the session (assuming providerId and companyName are stored in the session)
        String providerId = (String) session.getAttribute("providerId");
        String companyName = (String) session.getAttribute("companyName");
        System.out.println("In add jobs..provider id :" + providerId);
        System.out.println("In add jobs..company name :" + companyName);

        // Check if providerId and companyName are available in the session
        if (providerId != null && companyName != null) {
            // Set the providerId and companyName for the job
            job.setProviderId(providerId);  // Automatically link job to the provider
            job.setCompanyName(companyName);  // Automatically set company name
        } else {
            // Handle case where providerId or companyName is not available in session (e.g., user not logged in)
            return "Error: Provider details not found.";
        }

        // MongoDB will automatically generate the id for the job if it's not already set
        jobRepository.save(job);  // Save the job to the database

        return "Job added successfully!";
    }


	
	public List<Job> getJobsPro(String providerId) {
		System.out.println("Fetching jobs for providerId: " + providerId);
        return jobRepository.findByProviderId(providerId);
    }

	public JobProvider getProviderByEmail(String email) {
		System.out.println("Fetching jobs for email : " + email);
        return jobProviderRepository.findByEmail(email);
	}

	public JobProvider getProviderById(String providerId) {
		System.out.println("Fetching jobs for providerId : " + providerId);
        return jobProviderRepository.findByProviderId(providerId);
	}

	public void saveProvider(JobProvider existingProvider) {
		jobProviderRepository.save(existingProvider);
		
	}
	
	
//	public List<Job> viewJobs(HttpSession session) {
//	    String email = (String) session.getAttribute("email");
//	    System.out.println("In Job-provider controller - viewJobs..with Email: " + email);
//
//	    // Fetch all jobs created by this provider
//	    List<Job> jobs = jobProviderService.getJobsByProviderEmail(email);  // Create this method in JobProviderService
//
//	    // Print or log the jobs and applicants for debugging
//	    jobs.forEach(job -> {
//	        System.out.println("Job Title: " + job.getTitle());
//	        System.out.println("Applicants: " + job.getApplicants());
//	    });
//
//	    return jobs;
//	}

//	private List<Job> getJobsByProviderEmail(String email) {
//		// TODO Auto-generated method stub
//		return jobRepository.findByProviderEmail(email); 
//	}

	

  }
    