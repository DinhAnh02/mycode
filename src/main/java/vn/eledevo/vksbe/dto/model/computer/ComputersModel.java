package vn.eledevo.vksbe.dto.model.computer;

import java.time.LocalDateTime;

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
    String name;
    String status;
    String brand;
    String type;
    String note;
    LocalDateTime updateAt;
    String updateBy;
}
