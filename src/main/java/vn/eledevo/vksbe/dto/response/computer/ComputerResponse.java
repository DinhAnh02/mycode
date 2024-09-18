package vn.eledevo.vksbe.dto.response.computer;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Long createAt;
    Long updateAt;
    String createBy;
    String updateBy;
}
