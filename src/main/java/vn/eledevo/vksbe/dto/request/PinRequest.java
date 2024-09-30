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
public class PinRequest {
    @NotBlank(message = ResponseMessage.EXITS_PIN)
    @Size(min = 6, max = 6, message = ResponseMessage.PIN_SIZE)
    String pin;

    String pin2;
}
