package vn.eledevo.vksbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
    @OneToMany(mappedBy = "citizens", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CasePerson> casePersons;
}
