package vn.eledevo.vksbe.entity;

import java.time.LocalDate;
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
    String avatar;
    String organization;
    String position;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;

    @OneToMany(mappedBy = "citizens", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CasePerson> casePersons;

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
