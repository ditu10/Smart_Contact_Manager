package com.dsi.scm.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "This field cannot be empty!!")
    private String name;
    @Column(unique = true)
    @NotBlank(message = "This field cannot be empty!!")
    private String email;
    @NotBlank(message = "This field cannot be empty!!")
    @Size(min = 3, message = "length of password must be greater than 2!!")
    private String password;
    private String role;
    private boolean enabled;
    @Column(length = 2000)
    @NotBlank(message = "This field cannot be empty!!")
    @Size(max = 2000, message = "length of this field cannot exceed 2000!")
    private String about;
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Contact> contacts ;

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", about='" + about + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
