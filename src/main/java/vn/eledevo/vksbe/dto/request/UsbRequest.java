package vn.eledevo.vksbe.dto.request;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsbRequest {
    String usbCode;
    String createByAccountName;
    String status;
    LocalDateTime fromDate;
    LocalDateTime toDate;
}
