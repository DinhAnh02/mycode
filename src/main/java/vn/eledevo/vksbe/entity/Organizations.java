package vn.eledevo.vksbe.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.SecurityUtils;

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
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
        this.createdBy = SecurityUtils.getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
        this.updatedBy = SecurityUtils.getUserName();
    }
}
