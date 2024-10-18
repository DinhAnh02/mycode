package vn.eledevo.vksbe.dto.request.cases;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.CaseType;
import vn.eledevo.vksbe.constant.ResponseMessage;

import static vn.eledevo.vksbe.constant.ResponseMessage.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseCreateRequest {
    @NotBlank(message = CASE_NAME_CANNOT_BE_BLANK)
    @Pattern(regexp = "^[a-zA-Z0-9\\sÀ-ỹà-ỹ]*$", message = CASE_NAME_SPECIAL)
    @Size(max = 255, message = CASE_NAME_CANNOT_EXCEED_255_CHARACTER)
    String name;
    @NotBlank(message = CASE_CODE_CANNOT_BE_BLANK)
    @Pattern(regexp = "^[a-zA-Z0-9\\sÀ-ỹà-ỹ]*$", message =  CASE_CODE_SPECIAL)
    @Size(max = 255, message = CASE_CODE_CANNOT_EXCEED_255_CHARACTER)
    String code;
    @NotNull(message = DEPARTMENT_ID_NOT_NULL)
    Long departmentId;
    @NotBlank(message = DEPARTMENT_ID_NOT_NULL)
    String departmentName;
    CaseType caseType;
    String description;
}