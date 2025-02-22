package com.animesh245.backend.entity;

import com.animesh245.backend.enums.Role;
import com.animesh245.backend.enums.WorkSchedule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee implements Serializable, UserDetails {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_schedule")
    private WorkSchedule workSchedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

//    @Column(name = "start_time")
//    private LocalDateTime projectStartTime;
//
//    @Column(name = "end_time")
//    private LocalDateTime projectEndTime;

//    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "deptManager")
//    private Department department; //department manager

//    @JoinColumn(name = "current_project", referencedColumnName = "id")
//    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    private Project currentProject;

//    @Column(name = "login_time")
//    private LocalDateTime loginTime;
//
//    @Column(name = "logout_time")
//    private LocalDateTime logoutTime;

    @Column(name = "on_leave")
    private Boolean onLeave;

    @Column(name = "doj")
    private LocalDate dateOfJoin;

    @Column(name = "path")
    private String profilePhotoPath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "works_in", referencedColumnName = "id")
    private Department worksIn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_projects", joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")})
    private Set<Project> projects;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee")
    private Set<Dependent> dependents;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> role.name());
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
