package vn.eledevo.vksbe.dto.request.account;

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
    @Size(min = 1, max = 255, message = USERNAME_SIZE)
    String username;

    @NotBlank(message = FULL_NAME_REQUIRE)
    @Size(min = 1, max = 255, message = FULL_NAME_SIZE)
    String fullName;

    String avatar;

    @NotNull(message = ROLE_ID_REQUIRE)
    Long roleId;

    @NotBlank(message = ROLE_NAME_REQUIRE)
    @Size(min = 1, max = 255, message = ROLE_CODE_SIZE)
    String roleName;

    @NotNull(message = DEPARTMENT_REQUIRE)
    Long departmentId;

    @NotBlank(message = DEPARTMENT_NAME_REQUIRE)
    String departmentName;

    @NotNull(message = ORGANIZATION_REQUIRE)
    Long organizationId;

    @NotBlank(message = ORGANIZATION_NAME_REQUIRE)
    String organizationName;

    @NotBlank(message = PHONE_NUMBER_REQUIRE)
    @Pattern(regexp = RegexPattern.PHONE_NUMBER, message = PHONE_NUMBER_INVALID)
    String phoneNumber;
}
