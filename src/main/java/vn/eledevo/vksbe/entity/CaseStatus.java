package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;
import java.util.List;

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
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;

    @OneToMany(mappedBy = "case_status", orphanRemoval = true, fetch = FetchType.LAZY)
    List<Cases> cases;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = SecurityUtils.getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = SecurityUtils.getUserName();
    }
}
