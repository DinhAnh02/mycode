package vn.eledevo.vksbe.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "case_person")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CasePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String type;
    Boolean isDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caseId", nullable = false)
    Cases cases;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenId", nullable = false)
    Citizens citizens;
}
