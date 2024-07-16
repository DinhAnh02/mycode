package vn.eledevo.vksbe.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.TokenType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String accessToken;

    @Enumerated(EnumType.STRING)
    TokenType tokenType = TokenType.BEARER;

    Boolean isExpired;
    Boolean isRevoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;
}
