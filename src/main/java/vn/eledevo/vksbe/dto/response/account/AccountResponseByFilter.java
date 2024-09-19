package vn.eledevo.vksbe.dto.response.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseByFilter {
    String username;
    String fullName;
    Long roleId;
    String roleName;
    Long departmentId;
    String departmentName;
    Long organizationId;
    String organizationName;
    String status;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
