package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListUsbResponse {
    Long id;
    String status;
    String name;
    String fullName;
    LocalDateTime createAt;
}
