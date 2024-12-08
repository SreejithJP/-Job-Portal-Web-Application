package com.springBoot.JobSpot.backend.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applications")
public class Application {

    @Override
	public String toString() {
		return "Application [id=" + id + ", jobId=" + jobId + ", jobSeekerId=" + jobSeekerId + ", status=" + status
				+ ", jobseekerName=" + jobseekerName + ", jobseekerEmail=" + jobseekerEmail + ", skills="
				+ Arrays.toString(skills) + ", JobseekerExperience=" + JobseekerExperience + "]";
	}
	@Id
    private String id;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobSeekerId() {
		return jobSeekerId;
	}
	public void setJobSeekerId(String jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	private String jobId;
    private String jobSeekerId;
    private Boolean status;
    private String jobseekerName;
    public String getJobseekerName() {
		return jobseekerName;
	}
	public void setJobseekerName(String jobseekerName) {
		this.jobseekerName = jobseekerName;
	}
	public String getJobseekerEmail() {
		return jobseekerEmail;
	}
	public void setJobseekerEmail(String jobseekerEmail) {
		this.jobseekerEmail = jobseekerEmail;
	}
	public String[] getSkills() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills = skills;
	}
	public int getJobseekerExperience() {
		return JobseekerExperience;
	}
	public void setJobseekerExperience(int jobseekerExperience) {
		JobseekerExperience = jobseekerExperience;
	}
	private String jobseekerEmail;
    private String[] skills;
    private int JobseekerExperience; 
}
