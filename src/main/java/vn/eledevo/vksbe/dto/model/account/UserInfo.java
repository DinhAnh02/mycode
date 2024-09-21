package vn.eledevo.vksbe.dto.model.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {
    String avatar;
    String fullName;
    String gender;
    String phoneNumber;
    Boolean isConditionLogin1;
    Boolean isConditionLogin2;
    String roleCode;
    String departmentCode;
}
