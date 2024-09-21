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
@Table(name = "departments")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String code;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;

    @OneToMany(mappedBy = "departments", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Accounts> accounts;

    @OneToMany(mappedBy = "departments", orphanRemoval = true, fetch = FetchType.EAGER)
    List<MindmapTemplate> mindmapTemplates;

    @OneToMany(mappedBy = "departments", orphanRemoval = true, fetch = FetchType.LAZY)
    List<Cases> cases;

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
