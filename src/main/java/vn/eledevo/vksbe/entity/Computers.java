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
@Table(name = "computers")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Computers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String code;
    String status;
    String brand;
    String type;
    String note;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Accounts accounts;
}
