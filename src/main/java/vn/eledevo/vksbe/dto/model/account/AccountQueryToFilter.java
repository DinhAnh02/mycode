package vn.eledevo.vksbe.dto.model.account;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountQueryToFilter {
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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isShowLockButton;
    Boolean isShowUnlockButton;
    Boolean isEnabledLockButton;
    Boolean isEnabledUnlockButton;
}
