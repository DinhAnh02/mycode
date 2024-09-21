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
public class pinRequest {
    @NotBlank(message = ResponseMessage.EXITS_PIN)
    @Size(min = 6, message = ResponseMessage.PASSWD_SIZE)
    String pin;

    String pin2;
}
