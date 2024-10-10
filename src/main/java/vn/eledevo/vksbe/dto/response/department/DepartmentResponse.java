package vn.eledevo.vksbe.dto.response.department;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    Long id;
    String name;
    String code;
    String leader;
    String organizationName;
    LocalDate createdAt;
    LocalDate updatedAt;
}
