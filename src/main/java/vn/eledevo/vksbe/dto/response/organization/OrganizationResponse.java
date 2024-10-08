package vn.eledevo.vksbe.dto.response.organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationResponse {
    Long id;

    String name;
    String code;
    String address;
}
