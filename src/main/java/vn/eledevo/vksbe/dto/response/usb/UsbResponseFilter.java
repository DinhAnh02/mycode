package vn.eledevo.vksbe.dto.response.usb;

import java.time.LocalDateTime;

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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    String accountFullName;
}
