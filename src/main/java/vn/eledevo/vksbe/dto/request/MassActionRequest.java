package vn.eledevo.vksbe.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MassActionRequest<ID> {

    @NotEmpty
    private Set<ID> ids;
}
