package vn.eledevo.vksbe.dto.model;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDeviceInfoKeyQuery {
    Long id;
    Long deviceInfoId;
    UUID userId;
    String keyUsb;
    String deviceUuid;
    Boolean isDeleted;
}
