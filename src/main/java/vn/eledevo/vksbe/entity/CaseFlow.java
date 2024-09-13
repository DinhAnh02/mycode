package vn.eledevo.vksbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "caseflow")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseFlow {
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caseId", nullable = false)
    Cases cases;
}
