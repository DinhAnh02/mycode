package vn.eledevo.vksbe.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
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
    String dataLink;
    @Column(columnDefinition = "LONGTEXT")
    String dataNode;
    @Size(max = 1000)
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
        this.updatedAt = LocalDate.now();
        this.createdBy = SecurityUtils.getUserName();
        this.updatedBy = SecurityUtils.getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.createdBy = SecurityUtils.getUserName();
        this.updatedBy = SecurityUtils.getUserName();
    }
}
