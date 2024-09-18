package vn.eledevo.vksbe.dto.request;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
    String username;
    String fullName;
    String roleId;
    String roleName;
    Integer departmentId;
    String departmentName;
    Integer organizationId;
    String organizationName;
    String status;
    LocalDateTime fromDate;
    LocalDateTime toDate;
}
