package vn.eledevo.vksbe.dto.response.account;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result<T> {
    List<T> content;
    Long total;
}
