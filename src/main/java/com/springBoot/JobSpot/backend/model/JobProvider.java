package com.springBoot.JobSpot.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "JobProviders")
public class JobProvider {

    @Override
    public String toString() {
        return "JobProvider [providerId=" + providerId + ", companyName=" + companyName + ", email=" + email
                + ", password=" + password + ", location=" + location + ", address=" + address + ", establishedDate="
                + establishedDate + ", businessType=" + businessType + ", industryType=" + industryType + ", contactNumber=" + contactNumber + "]";
    }

    @Id
    private String providerId;
    private String companyName;
    private String email;
    private String password;
    private String location;
    private String address;
    private String establishedDate; // Changed to String to handle the date input in a simple way (you can change to Date type if necessary)
    private String businessType;
    private String industryType;
    private String contactNumber;

    public JobProvider() {
        System.out.println("in JobProvider default constructor..");
        //this.providerId = "default-id";
        this.companyName = "Default Company";
        this.email = "default@example.com";
        this.password = "defaultPassword";
        this.location = "Default Location";
        this.address = "Default Address";
        this.establishedDate = "01-01-2000";
        this.businessType = "Product-based"; // Default value
        this.industryType = "IT"; // Default value
        this.contactNumber = "0000000000";
    }

    // Getters and Setters
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(String establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
