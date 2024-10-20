package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseMindmapTemplateResponse<T> {
    String departmentName;
    List<T> content;
    Integer totalRecords;
    Integer pageSize;
    Integer page;
    Integer totalPages;
}
