package com.springBoot.JobSpot.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.springBoot.JobSpot.backend.model.*;
import com.springBoot.JobSpot.backend.repository.JobSeekerRepository;
import com.springBoot.JobSpot.backend.service.JobSeekerService;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

//    @GetMapping("/jobs")
//    public List<Job> listJobs() {
//        System.out.println("In Jobs controller../jobs");
//        return jobSeekerService.getJobs();
//    }
    @GetMapping("/home")
    public String showHomePage(HttpSession session) {
    	System.out.println("In Jobs controller../home");
	 if (session.getAttribute("loggedIn") == null) {
	        return "redirect:/auth/login";
	    }
	     
        return "jobSeekerHome"; // Corresponds to jobProviderHome.html in templates
    }
    @GetMapping("/show-jobs")
    public ResponseEntity<?> getJobsForProvider(HttpSession session) {
    	
    	if (session.getAttribute("loggedIn") == null) {
    	    return ResponseEntity.status(HttpStatus.FOUND) // 302 Found for redirection
    	                         .header("Location", "/auth/login")
    	                         .build();
    	}
        //String seekerId = (String) session.getAttribute("seekerId");
        List<Job> jobs = jobSeekerService.getJobs();

        if (!jobs.isEmpty()) {
            // Return the list of jobs if available
            return ResponseEntity.ok(jobs);
        } else {
            // Return a 404 if no jobs are found for the provider
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "No jobs found for provider."));
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyJob(@RequestBody Application application, HttpSession session) {
        System.out.println("In Jobs controller../apply");

        String responseMessage = jobSeekerService.applyToJob(application, session);

        // Return response message in JSON format
        if (responseMessage.equals("Application successful!")) {
            return ResponseEntity.ok(Collections.singletonMap("message", responseMessage));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Collections.singletonMap("message", responseMessage));
        }
    }

    @GetMapping("/profile/view")
    public String showProfilePage(HttpSession session) {
		System.out.println("jobSeeker/showProfilePage");
    	if (session.getAttribute("loggedIn") == null) {
    		System.out.println("jobSeeker/profile/view");
	        return "redirect:/auth/login";
	    }
        return "jobSeekerProfile"; // Corresponds to jobProviderProfile.html in templates
    }
    
   
	@GetMapping("/appliedJobs")
	public String showAppliedJobs(HttpSession session) {
	    System.out.println("jobSeeker/showAppliedJobs");
	    if (session.getAttribute("loggedIn") == null) {
	        System.out.println("User not logged in. Redirecting to login.");
	        return "redirect:/auth/login";
	    }
	    return "jobSeekerAppliedJobs"; // Ensure this matches the HTML filename
	}

	
	 @GetMapping("/profile")
	    public ResponseEntity<?> getSeekerProfile(HttpSession session) {
	    	System.out.println("in seeker/profile");
	        String seekerId = (String) session.getAttribute("seekerId");
	        if (session.getAttribute("loggedIn") == null) {
	            return ResponseEntity.status(HttpStatus.FOUND) // 302 Found for redirection
	                                 .header("Location", "/auth/login")
	                                 .build();
	        }
	        if (seekerId == null) {
	        	System.out.println("in seeker/profile--if seekerId==null");
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body("{\"message\": \"User not logged in.\"}");
	        }
	        System.out.println("in seeker/profile--fetching details from database");
	        // Fetch provider details from the database using the providerId
	        JobSeeker seeker = jobSeekerService.getSeekerById(seekerId);
	        System.out.println("in seeker/profile--fetched the details from database");
	        if (seeker != null) {
	        	 System.out.println("in seeker/profile--Return seeker details");
	            // Return the provider details, excluding providerId
	        //    provider.setProviderId(null);  // Remove the providerId from the response
	        	// System.out.println("Seeker : "+seeker);
	            return ResponseEntity.ok(seeker);  // Return provider details without providerId
	        } else {
	        	 System.out.println("in seeker/profile--seeker not found");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("{\"message\": \"seeker not found.\"}");
	        }
	    }
	 @PutMapping("/profile/update")
	 public ResponseEntity<?> updateJobSeekerProfile(@RequestBody JobSeeker updatedProfile, HttpSession session) {
	     System.out.println("Received updated profile data: " + updatedProfile);  // Debugging log

	     String seekerId = (String) session.getAttribute("jobSeekerId");

	     if (seekerId == null) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
	     }

	     JobSeeker existingJobSeeker = jobSeekerService.getSeekerById(seekerId);
	     if (existingJobSeeker != null) {
	         // Update only the allowed fields
	         existingJobSeeker.setSkills(updatedProfile.getSkills());
	         existingJobSeeker.setExperience(updatedProfile.getExperience());
	         existingJobSeeker.setEducation(updatedProfile.getEducation());
	         existingJobSeeker.setContactNumber(updatedProfile.getContactNumber());

	         // Save the updated profile
	         jobSeekerService.saveJobSeeker(existingJobSeeker);
	         return ResponseEntity.ok("Profile updated successfully!");
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found.");
	     }
	 }


	 @GetMapping("/about")
	    public String showAboutPage(HttpSession session) {
	    	if (session.getAttribute("loggedIn") == null) {
		        return "redirect:/auth/login";
		    }
	        return "seekerAbout"; // Corresponds to about.html in templates
	    }
	 
	 @GetMapping("/signup")
	    public String showSignUpPage() {
	        return "jobSeekerSignup"; // Returns jobSeekerSignup.html from templates
	    }

	 @PostMapping("/signup")
	 public ResponseEntity<?> registerJobSeeker(@RequestBody JobSeeker jobSeeker) {
	     // Retrieve email from the incoming JobSeeker object
	     String mail = jobSeeker.getEmail();
	     
	     // Check if a JobSeeker with the same email already exists
	     JobSeeker seeker = jobSeekerService.getSeekerByEmail(mail);
	     
	     // If the seeker exists, email is already in use
	     if (seeker != null) {
	         return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Sign-up failed. Email might already be in use."));
	     }
	     
	     // Otherwise, proceed to save the new JobSeeker
	     jobSeekerService.saveJobSeeker(jobSeeker);
	     
	     // Respond with a success message if the job seeker was saved successfully
	     return ResponseEntity.ok().body(Collections.singletonMap("message", "Sign-up successful!"));
	 }


}

