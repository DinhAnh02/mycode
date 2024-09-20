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
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    Departments departments;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.createBy = SecurityUtils.getUserName();
    }
    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
        this.updateBy = SecurityUtils.getUserName();
    }
}
