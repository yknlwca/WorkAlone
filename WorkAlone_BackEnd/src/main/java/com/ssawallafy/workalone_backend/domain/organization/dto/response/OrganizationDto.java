package com.ssawallafy.workalone_backend.domain.organization.dto.response;

import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationDto {
    private Long id;
    private String name;

    public static OrganizationDto of(Organization organization) {
        return OrganizationDto.builder().
                id(organization.getId()).
                name(organization.getName()).
                build();
    }
}
