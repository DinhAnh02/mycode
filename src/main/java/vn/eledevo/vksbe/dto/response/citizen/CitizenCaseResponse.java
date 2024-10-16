package vn.eledevo.vksbe.dto.response.citizen;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CitizenCaseResponse {
    Long id;
    String fullName;
    String citizenId;
    String type;
    String profileImage;
}
