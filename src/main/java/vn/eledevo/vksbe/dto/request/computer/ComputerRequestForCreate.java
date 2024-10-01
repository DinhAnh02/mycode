package vn.eledevo.vksbe.dto.request.computer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerRequestForCreate {
    @NotBlank(message = "Tên máy tính không được để trống")
    @Size(max = 255, message = "Tên máy tính có tối đa 255 kí tự")
    String name;

    @NotBlank(message = "Mã máy tính không được để trống")
    String code;

    @NotBlank(message = "Tên hãng máy không được để trống")
    @Size(max = 255, message = "Tên hãng máy có tối đa 255 kí tự")
    String brand;

    @NotBlank(message = "Loại máy tính không được để trống")
    String type;

    String note;
}
