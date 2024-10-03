package vn.eledevo.vksbe.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerResponseFilter {
    Long id;
    String name;
    String accountFullName;
    String code;
    String status;
    String brand;
    String type;
    String note;
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;
}
