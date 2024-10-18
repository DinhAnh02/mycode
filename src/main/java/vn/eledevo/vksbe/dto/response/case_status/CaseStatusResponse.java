package vn.eledevo.vksbe.dto.response.case_status;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseStatusResponse {
    Long id;
    String name;
    String description;
    String createdBy;
    String updatedBy;
    LocalDate createdAt;
    LocalDate updatedAt;
    Boolean isDefault;
}
