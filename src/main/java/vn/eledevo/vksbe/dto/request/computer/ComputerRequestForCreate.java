package vn.eledevo.vksbe.dto.request.computer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerRequestForCreate {
    @NotBlank
    @Size(min = 0, max = 100)
    @Pattern(
            regexp = "^[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơẠ-ỹ\\s]*$",
            message = "Tên người dùng không được chứa ký tự đặc biệt")
    String name;

    @NotBlank
    @Size(min = 10, max = 100)
    String code;

    @NotBlank
    @Size(min = 0, max = 100)
    String brand;

    @NotBlank
    String type;

    @Size(min = 0, max = 100)
    String note;
}
