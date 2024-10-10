package vn.eledevo.vksbe.dto.request.case_status;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseStatusGetRequest {
    String name;
    LocalDate fromDate ;
    LocalDate toDate;
}
