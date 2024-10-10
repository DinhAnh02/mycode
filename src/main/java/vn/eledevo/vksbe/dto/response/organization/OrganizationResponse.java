package vn.eledevo.vksbe.dto.response.organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    Boolean isDefault;
    LocalDate updatedAt;
}
