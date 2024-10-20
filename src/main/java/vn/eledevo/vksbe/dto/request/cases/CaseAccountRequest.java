package vn.eledevo.vksbe.dto.request.cases;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseAccountRequest {
    List<CaseAccountUpdateRequest> casePersons;
}
