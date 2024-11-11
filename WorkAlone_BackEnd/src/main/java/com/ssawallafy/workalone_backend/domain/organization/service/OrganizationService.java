package com.ssawallafy.workalone_backend.domain.organization.service;

import com.ssawallafy.workalone_backend.domain.organization.dto.response.OrganizationDto;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import com.ssawallafy.workalone_backend.domain.organization.entity.OrganizationMapping;
import com.ssawallafy.workalone_backend.domain.organization.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.organization.exception.OrganizationException;
import com.ssawallafy.workalone_backend.domain.organization.repository.OranizationMappingRepository;
import com.ssawallafy.workalone_backend.domain.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OranizationMappingRepository oranizationMappingRepository;
    public List<OrganizationDto> getOrganizations(Long memberId) {
        List<OrganizationMapping> organizationMappings = oranizationMappingRepository.findByMemberId(memberId);

        List<OrganizationDto> organizationDtos = new ArrayList<>();
        for (OrganizationMapping organizationMapping : organizationMappings) {
            Organization organization = organizationRepository.findById(organizationMapping.getOrganization().getId())
                    .orElseThrow(() -> new OrganizationException(ErrorCode.ORGANIZATION_NOT_FOUND));
            organizationDtos.add(OrganizationDto.of(organization));
        }
        return organizationDtos;
    }
}
