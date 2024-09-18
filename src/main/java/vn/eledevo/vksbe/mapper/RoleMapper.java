package vn.eledevo.vksbe.mapper;

import org.springframework.stereotype.Component;
import vn.eledevo.vksbe.dto.response.role.RoleResponse;
import vn.eledevo.vksbe.entity.Roles;

import java.util.Objects;

@Component
public class RoleMapper {
    //Private constructor
    private RoleMapper() {}
    public static RoleResponse toResponse(Roles e) {
        if (Objects.isNull(e)) {
            return new RoleResponse();
        }

        return RoleResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .code(e.getCode())
                .build();
    }
}
