package vn.eledevo.vksbe.dto.request.mindmapTemplate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.eledevo.vksbe.constant.ResponseMessage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MindMapTemplateRequest {
    @NotNull(message = ResponseMessage.MINDMAPTEMPLATE_NOT_NULL)
    Long departmentId;

    @NotNull(message = ResponseMessage.MINDMAPTEMPLATE_NOT_NULL)
    @Size(max = 255, message = ResponseMessage.NAMEMINDMAP_MAX)
    String departmentName;

    @NotNull(message = ResponseMessage.MINDMAPTEMPLATE_NOT_NULL)
    @Size(max = 255, message = ResponseMessage.NAMEMINDMAP_MAX)
    @Pattern(regexp = "^[a-zA-Z0-9\\sÀ-ỹà-ỹ]*$", message = ResponseMessage.NAMEMINDMAP_SPECIAL)
    String name;
}
