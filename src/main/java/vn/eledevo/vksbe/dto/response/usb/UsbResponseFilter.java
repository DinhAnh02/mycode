package vn.eledevo.vksbe.dto.response.usb;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsbResponseFilter {
    Long id;
    String status;
    String name;
    String createByAccountName;
    LocalDateTime createAt;
}
