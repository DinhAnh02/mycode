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
@Table(name = "profiles")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;
    String phoneNumber;
    String avatar;
    String gender;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;

    @OneToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    Accounts accounts;
}
