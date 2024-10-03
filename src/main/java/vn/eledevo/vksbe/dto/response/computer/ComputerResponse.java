package vn.eledevo.vksbe.dto.response.computer;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerResponse {
    Long id;
    String name;
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
