package vn.eledevo.vksbe.mapper;

import vn.eledevo.vksbe.dto.response.organization.OrganizationResponse;
import vn.eledevo.vksbe.entity.Organizations;

import java.util.Objects;


public class OrganizationMapper {
    //Private constructor
    private  OrganizationMapper(){}
    public static OrganizationResponse toResponse(Organizations e) {
        if (Objects.isNull(e)) {
            return new OrganizationResponse();
        }

        return OrganizationResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .abbreviatedName(e.getAbbreviatedName())
                .address(e.getAddress())
                .build();
    }
}
