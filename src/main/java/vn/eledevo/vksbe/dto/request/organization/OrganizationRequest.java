package vn.eledevo.vksbe.dto.request.organization;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationRequest {
    @NotNull(message = ResponseMessage.ORGANIZATION_NAME_NOT_BLANK)
    @Size( min =4 , max =8, message= ResponseMessage.ORGANIZATION_NAME_SIZE )
    String name;

    @NotNull(message = ResponseMessage.ORGANIZATION_CODE_NOT_BLANK)
    @Size( min =4 , max =8, message= ResponseMessage.ORGANIZATION_CODE_SIZE )
    String code;

    @NotNull(message = ResponseMessage.ORGANIZATION_ADDRESS_NOT_BLANK)
    @Size(max =225, message= ResponseMessage.ORGANIZATION_ADDRESS_SIZE )
    String address;
}
