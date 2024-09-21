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
    String status;
    String name;
    String createByAccountName;
    LocalDateTime createAt;
}
