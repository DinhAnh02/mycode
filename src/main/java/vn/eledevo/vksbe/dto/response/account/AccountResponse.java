package vn.eledevo.vksbe.dto.response.account;

import java.time.LocalDate;

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
    LocalDate createdAt;
    LocalDate updatedAt;
    String createdBy;
    String updatedBy;
}
