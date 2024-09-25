package vn.eledevo.vksbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    LocalDate fromDate;
    LocalDate toDate;
}
