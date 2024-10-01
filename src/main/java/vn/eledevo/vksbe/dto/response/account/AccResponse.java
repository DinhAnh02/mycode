package vn.eledevo.vksbe.dto.response.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.dto.model.account.OldPositionAccInfo;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccResponse {
    OldPositionAccInfo account;
}
