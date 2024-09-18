package vn.eledevo.vksbe.dto.model.computer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComputersDto {

    Long id;

    String name;

    String status;

    LocalDateTime createAt;
}
