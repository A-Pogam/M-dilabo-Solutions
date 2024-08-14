package com.medilabo_solutions.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "Male|Female", message = "Gender must be 'Male' or 'Female'")
    private String gender;

    @Size(max = 100, message = "Postal address cannot be longer than 100 characters")
    private String postalAddress;

    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Phone number is invalid")
    @Size(max = 25, message = "Phone number cannot be longer than 25 characters")
    private String phoneNumber;

    public Patient() {}

    public Patient(Long id, String firstName, String lastName, Date dateOfBirth, String gender, String postalAddress, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.postalAddress = postalAddress;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
