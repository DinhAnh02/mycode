package vn.eledevo.vksbe.dto.response.account;

import java.time.LocalDateTime;

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
    String roleCode;
    Long roleId;
    Long departmentId;
    String departmentName;
    Long organizationId;
    String organizationName;
    String status;
    Boolean isConnectComputer;
    Boolean isConnectUsb;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Boolean isShowLockButton;
    Boolean isShowUnlockButton;
    Boolean isEnableLockButton;
    Boolean isEnableUnlockButton;
}
