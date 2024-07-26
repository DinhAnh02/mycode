package vn.eledevo.vksbe.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDeviceInfoKeyResponse {
    Long id;
    Long deviceInfoId;
    UUID userId;
    String keyUsb;
    Boolean isDeleted;
}
