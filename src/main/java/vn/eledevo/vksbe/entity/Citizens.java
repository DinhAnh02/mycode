package vn.eledevo.vksbe.entity;

import java.time.LocalDate;
import java.util.List;

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
@Table(name = "citizens")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String investigatorCode;
    String name;
    LocalDate dateOfBirth;
    String address;
    String gender;
    String identification;
    @Size(max = 1000)
    String avatar;
    String organization;
    String position;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;

    @OneToMany(mappedBy = "citizens", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<CasePerson> casePersons;

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
