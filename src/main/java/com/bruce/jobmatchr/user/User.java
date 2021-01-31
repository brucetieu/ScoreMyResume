package com.bruce.jobmatchr.user;

import javax.persistence.*;

@Entity
@Table(name = "users") // table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // values of id are automatically generated by db
    private Long id;  // primary key

    @Column(nullable = false, unique = true, length = 45)  // email cannot be null and is unique
    private String email;

    @Column(nullable = false, length = 64) // 64 for bcrypt encryted password
    private String password;

    // Name of this column in database is "reset_password_token"
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public void setEmail(String email) {
        this.email = email;
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
}