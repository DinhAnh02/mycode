package vn.eledevo.vksbe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerRequest {
    String name;
    String status;
    String usbCode;
    String fullName;
    String note;
}
