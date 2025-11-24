package com.Zipdrive.API.Model;
import jakarta.persistence.*;
@Entity
@Table(name = "profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String profileImageUrl;
    private String address;
    private String bankAccountNumber;
    private String ifscCode;
    private String accountHolderName;
    private String drivingLicenseImageUrl;
    private String govtIdImageUrl;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    public String getIfscCode() {
        return ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    public String getDrivingLicenseImageUrl() {
        return drivingLicenseImageUrl;
    }
    public void setDrivingLicenseImageUrl(String drivingLicenseImageUrl) {
        this.drivingLicenseImageUrl = drivingLicenseImageUrl;
    }
    public String getGovtIdImageUrl() {
        return govtIdImageUrl;
    }
    public void setGovtIdImageUrl(String govtIdImageUrl) {
        this.govtIdImageUrl = govtIdImageUrl;
    }
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", address='" + address + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", drivingLicenseImageUrl='" + drivingLicenseImageUrl + '\'' +
                ", govtIdImageUrl='" + govtIdImageUrl + '\'' +
                '}';
    }
}

