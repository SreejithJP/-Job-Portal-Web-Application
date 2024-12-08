package com.springBoot.JobSpot.backend.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {

	public Job() {
		System.out.println("In Jobs Model");
	}
	@Id
	private String id;
	@Override
	public String toString() {
		return "Job [id=" + id + ", providerId=" + providerId + ", title=" + title + ", description=" + description
				+ ", location=" + location + ", companyName=" + companyName + ", requiredSkills="
				+ Arrays.toString(requiredSkills) + ", experience=" + experience + ", applicants=" + applicants + "]";
	}

	private String providerId;
	
	

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String[] getRequiredSkills() {
		return requiredSkills;
	}

	public void setRequiredSkills(String[] requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	private String title;
	private String description;
	private String location;
	private String companyName;
	private String[] requiredSkills;
	private int experience;

	private List<Application> applicants;

    // Getters and Setters
    public List<Application> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Application> applicants) {
        this.applicants = applicants;
    }
    
	

}
