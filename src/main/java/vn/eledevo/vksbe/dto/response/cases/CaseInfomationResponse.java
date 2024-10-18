package vn.eledevo.vksbe.dto.response.cases;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseInfomationResponse {
    Long id;
    String name;
    String code;
    Long departmentId;
    String departmentName;
    String statusName;
    Long statusId;
    LocalDate createAt;
    String type;
    String description;
    String createdBy;
}
