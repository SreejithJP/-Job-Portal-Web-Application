package com.springBoot.JobSpot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springBoot.JobSpot.backend.model.Application;
//import com.springBoot.JobSeeker.backend.model.Application;
import com.springBoot.JobSpot.backend.model.Job;
import com.springBoot.JobSpot.backend.model.JobProvider;
//import com.springBoot.JobSeeker.backend.repository.JobRepository;
import com.springBoot.JobSpot.backend.service.JobProviderService;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/job-provider")
public class JobProviderController {

	@Autowired
    private JobProviderService jobProviderService;
//    @Autowired
//    private Job job;
	 @GetMapping("/home")
	    public String showHomePage(HttpSession session) {
		 if (session.getAttribute("loggedIn") == null) {
		        return "redirect:/auth/login";
		    }
		     
	        return "jobProviderHome"; // Corresponds to jobProviderHome.html in templates
	    }

	    @GetMapping("/addjob")
	    public String showAddJobForm(HttpSession session) {
	    	if (session.getAttribute("loggedIn") == null) {
		        return "redirect:/auth/login";
		    }
	        return "jobProviderAddjob"; // Corresponds to jobProviderAddjob.html in templates
	    }

	    @GetMapping("/profile/view")
	    public String showProfilePage(HttpSession session) {
	    	if (session.getAttribute("loggedIn") == null) {
		        return "redirect:/auth/login";
		    }
	        return "jobProviderProfile"; // Corresponds to jobProviderProfile.html in templates
	    }

	    @GetMapping("/about")
	    public String showAboutPage(HttpSession session) {
	    	if (session.getAttribute("loggedIn") == null) {
		        return "redirect:/auth/login";
		    }
	        return "about"; // Corresponds to about.html in templates
	    }
 // Display the Add Job form
//    @GetMapping("/addjob")
//    public String showAddJobForm() {
//        return "jobProviderAddjob";  // This refers to addjob.html in static or templates
//    }
    @PostMapping("/add-job")
    public ResponseEntity<String> addJob(@RequestBody Job job, HttpSession session, Model model) {
        // Pass the session to the service method to automatically set providerId and companyName
        System.out.println("In controller class addJob...");
        if (session.getAttribute("loggedIn") == null) {
            return ResponseEntity.status(HttpStatus.FOUND) // 302 Found for redirection
                                 .header("Location", "/auth/login")
                                 .build();
        }
//        String authResult = SessionAuth(session);
//        if (authResult != null) {
//            return "login";  // Redirect to login if session is invalid
//        }
        // Call service method to add job, passing the session to automatically set providerId and companyName
        String result = jobProviderService.addJob(job, session);
        System.out.println("Result :"+result);
        // Add success message to the model
        model.addAttribute("message", result);  // result will be "Job added successfully!" or an error message

        // Return to the same addJobForm page
        return ResponseEntity.ok().body("{\"message\": \"Job added successfully!\"}");

    }


//    @GetMapping("/job-applications")
//    public List<?> viewApplications(@RequestParam String jobId) {
//        System.out.println("In controller class viewApplications..id :" + jobId);
//        
//        return jobProviderService.getApplications(jobId);
//    }
    @GetMapping("/job-applications")
    public String showApplicantsPage(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/auth/login";  // Redirect to login if not logged in
        }
        System.out.println("In /jobProvider/job-applications");
        return "jobProviderApplicants";  // Return the HTML template name (e.g., jobSeekerApplicants.html)
    }
    
    @GetMapping("/job-applications/view")
    public ResponseEntity<?> viewApplications(@RequestParam String jobId) {
        List<Application> applicants = jobProviderService.getApplicantsByJobId(jobId);

        if (applicants.isEmpty()) {
            // Return 404 with a message if no applicants are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Collections.singletonMap("message", "No applicants found."));
        }
        // Return the list of applicants in JSON format
        return ResponseEntity.ok(applicants);
    }


//    @GetMapping("/show-jobs")
//    public Optional<Job> viewJobs(HttpSession session) {
//    	String providerId=(String) session.getAttribute("providerId");
//        System.out.println("In Job-provider controller - viewJobs..with providerId :" + providerId);
//        
//        return jobProviderService.getJobs(providerId);
//    }
    
    @GetMapping("/show-jobs")
    public ResponseEntity<?> getJobsForProvider(HttpSession session) {
    	
    	if (session.getAttribute("loggedIn") == null) {
    	    return ResponseEntity.status(HttpStatus.FOUND) // 302 Found for redirection
    	                         .header("Location", "/auth/login")
    	                         .build();
    	}
        String providerId = (String) session.getAttribute("providerId");
        List<Job> jobs = jobProviderService.getJobs(providerId);

        if (!jobs.isEmpty()) {
            // Return the list of jobs if available
            return ResponseEntity.ok(jobs);
        } else {
            // Return a 404 if no jobs are found for the provider
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "No jobs found for provider."));
        }
    }

    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody JobProvider provider) {
        try {
            // Retrieve email from the incoming JobProvider object
            String mail = provider.getEmail();
            
            // Check if a JobProvider with the same email already exists
            JobProvider existingProvider = jobProviderService.getProviderByEmail(mail);
            
            // If a provider with the email already exists, return a bad request
            if (existingProvider != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body(Collections.singletonMap("message", "Email is already in use. Please choose a different email."));
            }

            // Proceed to register the provider if email is not taken
            jobProviderService.registerProvider(provider);

            return ResponseEntity.ok(Collections.singletonMap("message", "Job Provider registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering Job Provider: " + e.getMessage());
        }
    }

    
    @GetMapping("/signup")
    public String signupPage() {
    	System.out.println("in provider/signup");
        return "providerSignup"; // This returns providerSignup.html from the templates folder
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProviderProfile(HttpSession session) {
    	System.out.println("in provider/profile");
        String providerId = (String) session.getAttribute("providerId");
        if (session.getAttribute("loggedIn") == null) {
            return ResponseEntity.status(HttpStatus.FOUND) // 302 Found for redirection
                                 .header("Location", "/auth/login")
                                 .build();
        }
        if (providerId == null) {
        	System.out.println("in provider/profile--if providerId==null");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"message\": \"User not logged in.\"}");
        }
        System.out.println("in provider/profile--fetching details from database");
        // Fetch provider details from the database using the providerId
        JobProvider provider = jobProviderService.getProviderById(providerId);
        System.out.println("in provider/profile--fetched the details from database");
        if (provider != null) {
        	 System.out.println("in provider/profile--Return provider details");
            // Return the provider details, excluding providerId
        //    provider.setProviderId(null);  // Remove the providerId from the response
            
            return ResponseEntity.ok(provider);  // Return provider details without providerId
        } else {
        	 System.out.println("in provider/profile--Provider not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Provider not found.\"}");
        }
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateProviderProfile(@RequestBody JobProvider updatedProfile, HttpSession session) {
        String providerId = (String) session.getAttribute("providerId");

        if (providerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        JobProvider existingProvider = jobProviderService.getProviderById(providerId);
        if (existingProvider != null) {
            // Update only the allowed fields
            existingProvider.setLocation(updatedProfile.getLocation());
            existingProvider.setAddress(updatedProfile.getAddress());
            existingProvider.setEstablishedDate(updatedProfile.getEstablishedDate());
            existingProvider.setBusinessType(updatedProfile.getBusinessType());
            existingProvider.setIndustryType(updatedProfile.getIndustryType());
            existingProvider.setContactNumber(updatedProfile.getContactNumber());

            jobProviderService.saveProvider(existingProvider);
            return ResponseEntity.ok("Profile updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found.");
        }
    }



}
