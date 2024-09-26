package vn.eledevo.vksbe.dto.response.account;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.account.OldPositionAccInfo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivedAccountResponse {
    OldPositionAccInfo oldPositionAccInfo;
    String message;
}
