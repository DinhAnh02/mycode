package vn.eledevo.vksbe.dto.request.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDepartment {
    @NotBlank(message = "Không được để trống tên phòng ban")
    @Size(min = 1, max = 50, message = "Tên phòng ban không được vượt quá 50 ký tự")
    String departmentName;
}
