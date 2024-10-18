package vn.eledevo.vksbe.dto.request.cases;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseUpdateRequest {
    @Size(min = 1, max = 255,message = "Tên vụ án có kích thước từ 1 đến 255 kí tự")
    String name;
    @Size(min = 1, max = 255,message = "Mã vụ án có kích thước từ 1 đến 255 kí tự")
    String code;
    Long statusId;
    @Size(min = 1, max = 255,message = "Kiểu vụ án có kích thước từ 1 đến 255 kí tự")
    String type;
    @Size(max = 255, message = "Chú thích có kích thước tối đa là 255 kí tự")
    String description;
}
