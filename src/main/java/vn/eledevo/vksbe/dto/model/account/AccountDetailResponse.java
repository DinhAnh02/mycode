package vn.eledevo.vksbe.dto.model.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
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
    String avatar;
    /** Check hiển thị tất cả các nút */
    Boolean isDisplayAllButton;
    /** true: trạng thái đang hoạt động của tài khoản */
    Boolean isActive;
    /** true: nút kích hoạt tài khoản bị disable, false: nút kích hoạt tài khoản không bị disable */
    Boolean isDisableActiveButton;
    /** true: không hiển thị nút nào, false: hiển thị nút kích hoạt tài khoản/khoá */
    Boolean isReadOnly;
}
