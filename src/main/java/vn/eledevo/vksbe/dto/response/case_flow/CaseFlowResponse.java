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
    String dataLink;
    String dataNode;
    LocalDate createdAt;

    public CaseFlowResponse(Long id) {
        this.id = id;
    }

    public CaseFlowResponse(Long id, String name, String dataLink, String dataNode, String createdBy, String updatedBy, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.dataLink = dataLink;
        this.dataNode = dataNode;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
    }
}
