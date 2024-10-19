package vn.eledevo.vksbe.dto.request.citizens;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ResponseMessage;
import vn.eledevo.vksbe.utils.TrimData.Trimmed;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Trimmed
public class CitizensRequest {
    @NotBlank(message = ResponseMessage.CITIZEN_NAME_NOT_BLANK)
    @Size(max = 225, message = ResponseMessage.CITIZEN_NAME_SIZE)
    String fullName;
    @NotBlank(message = ResponseMessage.CITIZEN_ID_NOT_BANK)
    @Size(max = 225, message = ResponseMessage.CITIZEN_ID_SIZE)
    String citizenId;
    @NotBlank(message = ResponseMessage.CITIZEN_GENDER_NOT_BLANK)
    @Size(max = 4, message = ResponseMessage.CITIZEN_GENDER_SIZE)
    String gender;
    @Size(max = 225, message = ResponseMessage.ORGANIZATION_ADDRESS_SIZE)
    String address;
    @Size(max = 1000, message = ResponseMessage.CITIZEN_PROFILE_IMAGE_SIZE)
    String profileImage;
    @Size(max = 225, message = ResponseMessage.CITIZEN_WORKINGADDRESS_SIZE)
    String workingAddress;
    @Size(max = 225, message = ResponseMessage.CITIZEN_JOB_SIZE)
    String job;
}
