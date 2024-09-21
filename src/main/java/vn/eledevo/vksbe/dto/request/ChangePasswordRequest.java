package vn.eledevo.vksbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
