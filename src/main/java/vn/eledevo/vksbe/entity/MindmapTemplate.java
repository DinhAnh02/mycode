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
@Table(name = "mindmap_template")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MindmapTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String data;
    String url;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    Departments departments;

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
