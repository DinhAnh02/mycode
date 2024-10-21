package vn.eledevo.vksbe.dto.request.usb;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.TrimData.Trimmed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Trimmed
public class UsbTokenInfo {
    String usbCode;
    String usbVendorCode;
    String nameUsb;
}
