package vn.eledevo.vksbe.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
