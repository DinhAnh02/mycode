package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authtokens")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;
    String tokenType;
    Boolean isExpireTime;
    LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Accounts accounts;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }
}
