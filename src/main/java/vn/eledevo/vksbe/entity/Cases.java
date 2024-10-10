package vn.eledevo.vksbe.entity;

import java.time.LocalDate;
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
@Table(name = "cases")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;
    String name;
    String description;
    String caseType;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId", nullable = false)
    Departments departments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caseStatusId", nullable = false)
    CaseStatus case_status;

    @OneToMany(mappedBy = "cases", fetch = FetchType.LAZY)
    List<Documents> documents;

    @OneToMany(mappedBy = "cases", orphanRemoval = true, fetch = FetchType.EAGER)
    List<CaseFlow> caseFlows;

    @OneToMany(mappedBy = "cases", fetch = FetchType.EAGER)
    List<CasePerson> casePersons;

    @OneToMany(mappedBy = "cases")
    List<AccountCase> accountCases;

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
