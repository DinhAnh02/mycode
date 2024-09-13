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
@Table(name = "usbs")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String usbCode;
    String usbVendorCode;
    String keyUSB;
    String status;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    Accounts accounts;
}
