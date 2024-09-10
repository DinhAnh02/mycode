package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAuthenticationResponse {
    String keyUsb;
    String deviceUuid;
}
