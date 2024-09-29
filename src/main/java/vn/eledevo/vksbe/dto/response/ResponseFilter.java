package vn.eledevo.vksbe.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
