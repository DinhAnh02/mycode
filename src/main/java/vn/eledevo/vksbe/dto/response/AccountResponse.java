package vn.eledevo.vksbe.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
}
