package vn.eledevo.vksbe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime expireTime;
    LocalDateTime createTime;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Accounts accounts;
}
