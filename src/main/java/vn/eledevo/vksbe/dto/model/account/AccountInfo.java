package vn.eledevo.vksbe.dto.model.account;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfo {
    String roleCode;
    Long departmentId;
    Boolean isConnectComputer;
    Boolean isConnectUsb;
}