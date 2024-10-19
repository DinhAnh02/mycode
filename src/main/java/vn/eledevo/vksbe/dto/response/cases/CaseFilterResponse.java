package vn.eledevo.vksbe.dto.response.cases;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseFilterResponse {
    Long id;
    String code;
    String name;
    String departmentName;
    String statusName;
    LocalDate updateAt;
}