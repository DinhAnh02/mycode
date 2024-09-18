package vn.eledevo.vksbe.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    Long id;
    String username;
    String status;
    Boolean isConditionLogin1;
    Boolean isConditionLogin2;
    Boolean isConnectComputer;
    Boolean isConnectUsb;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
}
