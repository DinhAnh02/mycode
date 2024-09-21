package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = ResponseMessage.USER_BLANK)
    String username;

    @NotBlank(message = ResponseMessage.PASSWD_BLANK)
    String password;

    String currentDeviceId;
}
