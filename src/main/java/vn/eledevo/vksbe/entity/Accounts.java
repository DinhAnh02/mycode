package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    String employeeCode;
    String password;
    String status;
    Long pin;
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
    @JoinColumn(name = "roleCode", nullable = false)
    Roles roles;

    @OneToOne(mappedBy = "accounts", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    Profiles profile;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    List<AuthTokens> authTokens;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Computers> computers;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Usbs> usbs;

    @OneToMany(mappedBy = "accounts", orphanRemoval = true)
    List<AccountCase> accountCases;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
}
