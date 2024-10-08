package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PinChangeRequest {
    @NotBlank(message = ResponseMessage.EXITS_PIN_OLD)
    String oldPinCode;

    @NotBlank(message = ResponseMessage.EXITS_PIN)
    @Size(min = 6, max = 6, message = ResponseMessage.PIN_SIZE)
    String newPinCode;

    String confirmPinCode;
}
