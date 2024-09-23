package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerResponseFilter {
    Long id;
    String code;
    String brand;
    String type;
    String name;
    String status;
    String note;
    String accountFullName;
}
