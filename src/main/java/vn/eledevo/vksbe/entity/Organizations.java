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
@Table(name = "organizations")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String abbreviatedName;
    String address;
    Boolean isDefault;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
}
