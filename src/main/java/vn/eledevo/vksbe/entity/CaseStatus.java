package vn.eledevo.vksbe.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.SecurityUtils;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "case_status")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Column(unique = true, nullable = false)
    String code;

    String description;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;
    Boolean isDefault;

    @OneToMany(mappedBy = "case_status", fetch = FetchType.LAZY)
    List<Cases> cases;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.createdBy = SecurityUtils.getUserName();
        this.updatedBy = SecurityUtils.getUserName();
        this.code = UUID.randomUUID().toString();
    }

    @PreUpdate
    public void preUpdate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.createdBy = SecurityUtils.getUserName();
        this.updatedBy = SecurityUtils.getUserName();
    }
}
