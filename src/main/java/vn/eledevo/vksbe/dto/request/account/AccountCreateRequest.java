package vn.eledevo.vksbe.dto.request.account;

import static vn.eledevo.vksbe.constant.RegexPattern.ACCOUNT_FULL_NAME;
import static vn.eledevo.vksbe.constant.ResponseMessage.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.RegexPattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreateRequest {

    @NotBlank(message = USERNAME_REQUIRE)
    @Size(min = 8, max = 8, message = USERNAME_SIZE)
    String username;

    @NotBlank(message = FULL_NAME_REQUIRE)
    @Pattern(regexp = ACCOUNT_FULL_NAME, message = FULL_NAME_SIZE)
    @Size(max = 255, message = FULL_NAME_SIZE)
    String fullName;

    String avatar;

    @NotNull(message = ROLE_NAME_REQUIRE)
    Long roleId;
    String roleName;

    @NotNull(message = DEPARTMENT_NAME_REQUIRE)
    Long departmentId;
    String departmentName;

    @NotNull(message = ORGANIZATION_NAME_REQUIRE)
    Long organizationId;
    String organizationName;

    @NotBlank(message = PHONE_NUMBER_REQUIRE)
    @Pattern(regexp = RegexPattern.PHONE_NUMBER, message = PHONE_NUMBER_INVALID)
    String phoneNumber;
}
