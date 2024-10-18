package vn.eledevo.vksbe.dto.request.case_status;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseStatusGetRequest {
    String name;
    LocalDate fromDate;
    LocalDate toDate;
}
