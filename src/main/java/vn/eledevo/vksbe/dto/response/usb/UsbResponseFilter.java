package vn.eledevo.vksbe.dto.response.usb;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsbResponseFilter {
    Long id;
    String name;
    String usbCode;
    String usbVendorCode;
    String keyUsb;
    String status;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;
    String accountFullName;
}
