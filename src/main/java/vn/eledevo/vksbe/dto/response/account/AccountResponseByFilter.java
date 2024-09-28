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
    Long id;
    String username;
    String fullName;
    String roleName;
    String departmentName;
    String organizationName;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isShowLockButton;
    Boolean isShowUnlockButton;
    Boolean isEnabledLockButton;
    Boolean isEnabledUnlockButton;
}
