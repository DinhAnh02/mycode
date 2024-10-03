package vn.eledevo.vksbe.dto.response.account;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseByFilter {
    Long id;
    String username;
    String fullName;
    String roleName;
    String departmentName;
    String organizationName;
    String status;
    LocalDate createdAt;
    LocalDate updatedAt;
    Boolean isShowLockButton;
    Boolean isShowUnlockButton;
    Boolean isEnabledLockButton;
    Boolean isEnabledUnlockButton;
}
