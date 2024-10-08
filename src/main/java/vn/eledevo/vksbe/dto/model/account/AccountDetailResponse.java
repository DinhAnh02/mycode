package vn.eledevo.vksbe.dto.model.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailResponse {
    Long id;
    String username;
    String fullName;
    String gender;
    Long departmentId;
    String departmentName;
    Long roleId;
    String roleName;
    Long organizationId;
    String organizationName;
    String status;
    String phoneNumber;
    String avatar;

    /** Trạng thái của nút KHÓA TÀI KHOẢN */
    Boolean isEnabledLockButton;

    Boolean isShowLockButton;

    /** Trạng thái nút SỬA */
    Boolean isEnabledEditButton;

    Boolean isShowEditButton;

    /** Trạng thái nút RESET MẬT KHẨU */
    Boolean isEnabledResetPasswordButton;

    Boolean isShowResetPasswordButton;

    /** Trạng thái nút KÍCH HOẠT TÀI KHOẢN */
    Boolean isEnabledActivateButton;

    Boolean isShowActivateButton;
}
