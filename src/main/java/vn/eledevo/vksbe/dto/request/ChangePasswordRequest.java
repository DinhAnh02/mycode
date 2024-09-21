package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @NotBlank(message = ResponseMessage.OLD_PASSWD_BLANK)
    String oldPassword;

    @NotBlank(message = ResponseMessage.NEW_PASSWD_BLANK)
    @Size(min = 8, message = ResponseMessage.PASSWD_SIZE)
    String newPassword;

    String confirmPassword;
}
