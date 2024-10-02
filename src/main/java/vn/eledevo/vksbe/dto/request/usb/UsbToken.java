package vn.eledevo.vksbe.dto.request.usb;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsbToken {
    String usbCode;
    String usbVendorCode;
}
