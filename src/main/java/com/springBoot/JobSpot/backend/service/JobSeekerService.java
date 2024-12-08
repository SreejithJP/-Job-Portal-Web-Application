package com.springBoot.JobSpot.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springBoot.JobSpot.backend.model.Application;
import com.springBoot.JobSpot.backend.model.Job;
import com.springBoot.JobSpot.backend.model.JobProvider;
import com.springBoot.JobSpot.backend.model.JobSeeker;
import com.springBoot.JobSpot.backend.repository.JobRepository;
import com.springBoot.JobSpot.backend.repository.JobSeekerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class JobSeekerService {

	@Autowired
	private JobRepository jobRepository;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    private Application applicant = new Application();  // Or use a constructor if necessary



    public void saveJobSeeker(JobSeeker jobSeeker) {
        jobSeekerRepository.save(jobSeeker);
    }


    public JobSeeker getSeeker(String email) {
        return jobSeekerRepository.findByEmail(email);
    }
    
    public List<Job> getJobs() {
        List<Job> jobs = null;
		try {
			jobs = jobRepository.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Fetch all jobs
        //(skill == null || job.getRequiredSkills().contains(skill)) &&
		System.out.println("In JobseekerService - getjobs");
        return jobs; // Filter based on parameters
    }


    public String applyToJob(Application application, HttpSession session) {
        // Fetch job seeker details from session
        String jobSeekerId = (String) session.getAttribute("jobSeekerId");
        String jobSeekerName = (String) session.getAttribute("jobSeekerName");
        String jobSeekerEmail = (String) session.getAttribute("jobSeekerEmail");
        String[] jobSeekerSkills = (String[]) session.getAttribute("jobSeekerSkills");
        int jobSeekerExperience = (int) session.getAttribute("jobSeekerExperience");

        // Check if all session data is available
        if (jobSeekerId == null || jobSeekerName == null || jobSeekerEmail == null || jobSeekerSkills == null) {
            return "Job Seeker details are missing in the session.";
        }

        // Retrieve the job details based on jobId
        Optional<Job> jobOptional = jobRepository.findById(application.getJobId());
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();

            // Initialize the applicants list if null
            if (job.getApplicants() == null) {
                job.setApplicants(new ArrayList<>());
            }
 
            // Create a new applicant and set the details
            //Applicantion applicant = new Applicantion();
            applicant.setJobId(application.getJobId());
            applicant.setJobSeekerId(jobSeekerId);
            applicant.setJobseekerName(jobSeekerName);
            applicant.setJobseekerEmail(jobSeekerEmail);
            applicant.setSkills(jobSeekerSkills);  // Add skills from session
            applicant.setJobseekerExperience(jobSeekerExperience);

            // Add the applicant to the job's applicants list
            job.getApplicants().add(applicant);

            // Save the updated job with the new applicant in the database
            jobRepository.save(job);

            return "Application successful!";
        } else {
            return "Job not found!";
        }
    }

        // Fetch job seeker profile by ID
        public JobSeeker getJobSeekerById(String seekerId) {
            return jobSeekerRepository.findById(seekerId).orElse(null);
        }


    public JobSeeker getSeekerByEmail(String email) {
		System.out.println("Fetching jobs for email : " + email);
        return jobSeekerRepository.findByEmail(email);
	}

	public JobSeeker getSeekerById(String seekerId) {
		System.out.println("Fetching jobs for seekerId : " + seekerId);
        return jobSeekerRepository.findBySeekerId(seekerId);
	}
    }



