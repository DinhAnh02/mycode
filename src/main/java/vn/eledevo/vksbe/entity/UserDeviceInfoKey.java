package vn.eledevo.vksbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_device_info_key")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDeviceInfoKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String keyUsb;
    LocalDateTime createAt;
    UUID createBy;
    LocalDateTime updateAt;
    UUID updateBy;
    Boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_info_id", insertable = false, updatable = false)
    DeviceInfo deviceInfo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;
}
