package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequest {
    @NotBlank(message = ResponseMessage.USER_BLANK)
    @Size(min = 3, max = 50, message = ResponseMessage.USER_SIZE)
    String username;

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(max = 100, message = "Tên người dùng không được vượt quá 100 ký tự")
    String fullName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải có độ dài từ 6 đến 100 ký tự")
    String password;
}
