package vn.eledevo.vksbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.Role;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String fullName;
    String username;
    String password;
    Role role;
}
