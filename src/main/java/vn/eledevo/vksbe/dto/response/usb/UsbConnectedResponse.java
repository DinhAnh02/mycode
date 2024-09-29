package vn.eledevo.vksbe.dto.response.usb;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsbConnectedResponse {
    Long id;
    String name;
    String usbCode;
    String usbVendorCode;
}
