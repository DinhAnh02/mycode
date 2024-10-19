package vn.eledevo.vksbe.dto.request.cases;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseCitizenUpdateRequest {
    List<CaseCitizenRequest> requests;
}
