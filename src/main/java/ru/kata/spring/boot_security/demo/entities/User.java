package ru.kata.spring.boot_security.demo.entities;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "height")
    private double height; //рост
    @Column(name = "weight")
    private int weight; //вес
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String email, String password, String username, String lastName, double height, int weight) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.lastname = lastName;
        this.weight = weight;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getDiagnosis() {
        double index = weight/(height * height);
        String diagnosis = "not correct biometric data";
        if (index>40.0) {
            diagnosis = "grade III obesity";
        } else if (index>35.0 && index<=40.0) {
            diagnosis = "grade II obesity";
        } else if (index>30.0 && index<=35.0) {
            diagnosis = "grade I obesity";
        } else if (index>25.0 && index<=30.0) {
            diagnosis = "excess body weight";
        } else if (index>18.5 && index<=25.0) {
            diagnosis = "the norm of body weight";
        } else if (index>16.0 && index<=18.5) {
            diagnosis = "insufficient body weight";
        } else if (index>0 && index<=16.0) {
            diagnosis = "severe body weight deficiency";
        }
        return diagnosis;
    }

    @Override
    public String toString() {
        return id + " " + username + " " + lastname + ", рост-" + height + "м. вес-" + weight + "кг. ";
    }

    //////// for UserDetails ///////////////////////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
