package vn.eledevo.vksbe.dto.response.case_flow;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CaseFlowResponse {
    Long id;
    String name;
    String url;
    String createdBy;
    String updatedBy;
    LocalDate createdAt;

    public CaseFlowResponse(Long id) {
        this.id = id;
    }
}
