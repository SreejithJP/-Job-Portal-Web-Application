package com.springBoot.JobSpot.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springBoot.JobSpot.backend.model.JobProvider;
import com.springBoot.JobSpot.backend.model.JobSeeker;
import com.springBoot.JobSpot.backend.service.JobProviderService;
import com.springBoot.JobSpot.backend.service.JobSeekerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private JobProviderService jobProviderService;

    public AuthController() {
        System.out.println("In AuthController...");
    }

    @GetMapping("/check-session")
    public ResponseEntity<Void> checkSession(HttpSession session) {
        // Check if the 'loggedIn' attribute exists in the session
    	System.out.println("In Auth /check-session");
        if (session.getAttribute("loggedIn") != null) {
            // Session is valid
        	System.out.println("In Auth /check-session/session valid");
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            // Session is invalid or expired
        	System.out.println("In Auth /check-session/Invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }
    }
    
    // Display login form
    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("In AuthController - Displaying Login Form");
        return "login";  // This returns the "login.html" page
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,HttpSession session,
                        Model model) {

        System.out.println("In AuthController - Processing Login");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Check if the user is a Job Seeker
        JobSeeker seeker = jobSeekerService.getSeeker(email);
        if (seeker != null && seeker.getPassword().equals(password)) {
            System.out.println("Job Seeker login successful. Email: " + seeker.getEmail());
            String seekerId=seeker.getSeekerId();
        	session.setAttribute("jobSeekerName", seeker.getName());
        	session.setAttribute("jobSeekerEmail", seeker.getEmail());
        	session.setAttribute("jobSeekerId", seeker.getSeekerId());
        	session.setAttribute("jobSeekerSkills", seeker.getSkills());
        	session.setAttribute("jobSeekerExperience", seeker.getExperience());
        	
            System.out.println("Job Seeker login successful. seekerId: " + seekerId);
            System.out.println("Job Seeker login successful. email: " + seeker.getEmail());
          //  return "redirect:/job-provider/home";  // Redirect to Job Provider home page
            session.setAttribute("seekerId", seekerId);
            System.out.println("session set......."+(String)session.getAttribute("providerId"));
            session.setAttribute("loggedIn", true);
            return "jobSeekerHome";
        }

        // Check if the user is a Job Provider
        JobProvider provider = jobProviderService.getProviderByEmail(email);
        if (provider != null && provider.getPassword().equals(password)) {
        	String providerId=provider.getProviderId();
        	session.setAttribute("providerId", provider.getProviderId());
        	session.setAttribute("companyName", provider.getCompanyName());

            System.out.println("Job Provider login successful. providerId: " + providerId);
            System.out.println("Job Provider login successful. email: " + provider.getEmail() +" company name :"+provider.getCompanyName());
          //  return "redirect:/job-provider/home";  // Redirect to Job Provider home page
            session.setAttribute("providerId", providerId);
            System.out.println("session set......."+(String)session.getAttribute("providerId"));
            session.setAttribute("loggedIn", true);
            return "jobProviderHome";
        }
        
        // Invalid credentials: Show error on login page
    //    model.addAttribute("error", "Invalid email or password. Please try again.");
        model.addAttribute("error", "Invalid email or password.");
        return "login";  // Return to the login page with an error message
    }

    // Handle logout
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("AuthController - Logging Out");
        System.out.println("session data :"+session.getAttribute("providerId"));
        // Invalidate the session to remove all user-specific data
        session.invalidate();
        System.out.println("session data after INVALIDATION :");
        // Clear cache by adding response headers
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  // Prevent caching
        response.setHeader("Pragma", "no-cache");  // For HTTP/1.0 compatibility
        response.setDateHeader("Expires", 0);  // Expiry date to 0 to avoid caching

        // Prevent back navigation to the previous page after logout by setting headers
        response.setHeader("Cache-Control", "no-store");  // Disable caching for this response
        response.setHeader("Pragma", "no-cache");  // For HTTP/1.0 compatibility
        response.setHeader("Expires", "0");  // Expired date

        // Redirect to the login page
        return "login";  // Adjusted to redirect instead of returning a view name
    }


}

