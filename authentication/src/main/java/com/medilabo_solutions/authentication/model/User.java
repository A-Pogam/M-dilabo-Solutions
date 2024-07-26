package com.medilabo_solutions.authentication.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;


@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Email is mandatory")
    @Column (unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long.")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    public User(Integer id, String email, String password, String fullname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
