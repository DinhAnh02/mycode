package vn.eledevo.vksbe.dto.model.computer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputersModel {
    @Size(min = 10, message = "Tên máy tính phải có độ dài ít nhất từ 10 ký tự.")
    String name;

    @NotBlank(message = "Thương hiệu không được để trống.")
    String brand;

    @NotBlank(message = "Loại máy tính không được để trống.")
    String type;

    @Size(max = 224, message = "Mô tả không được vượt quá 225 kí tự.")
    String note;
}
