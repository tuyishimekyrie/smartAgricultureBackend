package com.sensor.agri.smartagriculturebackend.core.user.entity;

import com.fasterxml.jackson.annotation.*;
import com.sensor.agri.smartagriculturebackend.core.notification.entity.Notification;
import com.sensor.agri.smartagriculturebackend.core.resetToken.entity.ResetToken;
import com.sensor.agri.smartagriculturebackend.core.sensor.entity.Sensor;
import com.sensor.agri.smartagriculturebackend.core.user.enums.ERole;
import com.sensor.agri.smartagriculturebackend.utils.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends BaseEntity implements UserDetails {


    @NotBlank
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    private String lastname;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ERole role;

//    @OneToMany(mappedBy = "user")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
//    @JoinColumn(name = "sensor_id")
        @JsonIgnore
//    @JsonManagedReference
    public List<Sensor> sensor;

//    @OneToMany
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
//    @JsonIgnore
    public List<ResetToken> resetToken;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonBackReference(value = "notification-user")
//    @JsonIgnore
    private List<Notification> notifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
