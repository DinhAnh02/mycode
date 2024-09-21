package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accounts implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String status;
    String pin;
    Boolean isConditionLogin1;
    Boolean isConditionLogin2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId", nullable = false)
    Departments departments;

    Boolean isConnectComputer;
    Boolean isConnectUsb;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Roles roles;

    @OneToOne(mappedBy = "accounts", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    Profiles profile;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    List<AuthTokens> authTokens;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Computers> computers;

    @OneToOne(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    Usbs usb;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true)
    List<AccountCase> accountCases;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles != null
                ? Collections.singletonList(new SimpleGrantedAuthority(roles.getCode()))
                : Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.createBy = SecurityUtils.getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
        this.createBy = SecurityUtils.getUserName();
    }
}
