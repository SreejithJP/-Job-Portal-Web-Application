package com.springBoot.JobSpot.backend.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "JobSeekers")
public class JobSeeker {

	@Override
	public String toString() {
		return "JobSeeker [seekerId=" + seekerId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", skills=" + Arrays.toString(skills) + ", experience=" + experience + ", education=" + education
				+ ", contactNumber=" + contactNumber + "]";
	}
	public JobSeeker() {
		System.out.println("In JOBSEEKER class ..");
	}
    @Id
    private String seekerId;
   
	
	public String getSeekerId() {
		return seekerId;
	}
	public void setSeekerId(String seekerId) {
		this.seekerId = seekerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getSkills() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills = skills;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	private String name;
    private String email;
    private String password;
    private String[] skills;
    private int experience;
    private String education; // New field for Education
    public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	private String contactNumber; // New field for Contact Number
    
}
