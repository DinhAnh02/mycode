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
@Table(name = "documents")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String path;
    String type;
    Long size;
    String description;
    Boolean isDelete;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;

    @ManyToOne
    @JoinColumn(name = "caseId", nullable = false)
    Cases cases;

    @ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    Documents parentId;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Documents> childDocuments;

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
