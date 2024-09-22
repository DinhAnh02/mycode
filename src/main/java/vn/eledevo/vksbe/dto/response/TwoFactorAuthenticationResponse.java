package vn.eledevo.vksbe.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAuthenticationResponse {
    String maPin;
    String keyUsb;
    List<String> listDevices;
    String usbCode;
    String usbVendorCode;
}
