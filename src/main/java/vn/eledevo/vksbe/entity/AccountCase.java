package vn.eledevo.vksbe.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_case")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Boolean hasAccess;
    String accountRole;
    String position;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Accounts accounts;
    @ManyToOne
    @JoinColumn(name = "caseId", nullable = false)
    Cases cases;
}
