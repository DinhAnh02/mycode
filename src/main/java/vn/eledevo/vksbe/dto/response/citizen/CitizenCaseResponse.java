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
    String type;
    String fullName;
    String citizenId;
    String gender;
    String profileImage;
    String workingAddress;
    String address;
    String job;
}
