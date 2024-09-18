package vn.eledevo.vksbe.dto.response.computer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputerResponse {
    Long id;
    String name;
    String code;
    String status;
    String brand;
    String type;
    String note;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String createBy;
    String updateBy;
}
