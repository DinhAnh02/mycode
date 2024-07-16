package vn.eledevo.vksbe.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "device_info")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String deviceUuid;

    @Column(columnDefinition = "varchar(255) default 'Not Connect'")
    String status;

    LocalDateTime createAt;
    UUID createBy;
    LocalDateTime updateAt;
    UUID updateBy;
    Boolean isDeleted;

    @OneToMany(mappedBy = "deviceInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserDeviceInfoKey> userDeviceInfoKeys;
}
