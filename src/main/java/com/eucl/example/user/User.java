package com.eucl.example.user;

import com.eucl.example.meter.Meter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.eucl.example.role.Role;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@Table(name= "_user")
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Principal {


    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;

    @Column(name="national_id", unique = true)
    private String nationalId;

    @Column(name="phone", unique = true)
    private  String phone;


    private String password;
    private boolean accountLocked;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Meter> meters;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;



    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return email;
    }

    public String fullName() {
        return getFirstname() + " " + getLastname();
    }

    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }
}
