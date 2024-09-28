package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "histories")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String action;
    String impact;
    LocalDateTime createdAt;
    String createdBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = SecurityUtils.getUserName();
    }
}
