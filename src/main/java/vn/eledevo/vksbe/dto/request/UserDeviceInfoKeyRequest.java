package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

import java.util.UUID;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserDeviceInfoKeyRequest {
    @NotBlank(message = ResponseMessage.USER_ID_BLANK)
    UUID userId;
    @NotBlank(message = ResponseMessage.DEVICE_ID_BLANK)
    Long deviceInfoId;
}
