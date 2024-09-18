package vn.eledevo.vksbe.dto.response.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseByFilter {
    String username;
    String fullName;
    Long roleId;
    String roleName;
    Integer departmentId;
    String departmentName;
    Integer organizationId;
    String organizationName;
    String status;
    String createAt;
    String updateAt;
}
