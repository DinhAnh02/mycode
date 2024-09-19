package vn.eledevo.vksbe.dto.model.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailResponse {
    String username;
    String fullName;
    String departmentName;
    String roleName;
    String status;
    String phoneNumber;
    /** Check hiển thị tất cả các nút với role IT_ADMIN */
    Boolean isDisplayAllButton;
    /** true: trạng thái đang hoạt động của tài khoản */
    Boolean isActive;
    /** true: nút kích hoạt tài khoản bị disable, false: nút kích hoạt tài khoản không bị disable */
    Boolean isDisableActiveButton;
    /** true: không hiển thị nút nào, false: hiển thị nút kích hoạt tài khoản/khoá với role khác IT_ADMIN */
    Boolean isReadOnly;
}
