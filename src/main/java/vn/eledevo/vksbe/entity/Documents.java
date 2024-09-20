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
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;

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
        this.createAt = LocalDateTime.now();
        this.createBy = SecurityUtils.getUserName();
    }
    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
        this.updateBy = SecurityUtils.getUserName();
    }
}
