package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseFilter<T> {
    List<T> content;
    Integer totalRecords;
    Integer pageSize;
    Integer page;
    Integer totalPages;
}
