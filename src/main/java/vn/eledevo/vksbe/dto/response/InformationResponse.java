package vn.eledevo.vksbe.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.entity.Departments;
import vn.eledevo.vksbe.entity.Organizations;
import vn.eledevo.vksbe.entity.Roles;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class InformationResponse {
    List<Departments> departments;
    List<Organizations> organizations;
    List<Roles> roles;
}
