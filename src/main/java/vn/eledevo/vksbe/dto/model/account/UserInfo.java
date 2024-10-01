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
    Long id;
    String userName;
    String avatar;
    String fullName;
    String gender;
    String phoneNumber;
    String roleCode;
    String departmentCode;
    String organizationId;
    Boolean isConditionLogin1;
    Boolean isConditionLogin2;
}
