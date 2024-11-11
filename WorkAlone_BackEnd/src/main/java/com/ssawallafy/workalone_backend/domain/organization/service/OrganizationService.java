package com.ssawallafy.workalone_backend.domain.organization.service;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.member.exception.BusinessLogicException;
import com.ssawallafy.workalone_backend.domain.member.repository.MemberRepository;
import com.ssawallafy.workalone_backend.domain.organization.dto.request.OrganizationReqDto;
import com.ssawallafy.workalone_backend.domain.organization.dto.response.OrganizationDto;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import com.ssawallafy.workalone_backend.domain.organization.entity.OrganizationMapping;
import com.ssawallafy.workalone_backend.domain.organization.entity.OrganizationMappingId;
import com.ssawallafy.workalone_backend.domain.organization.exception.ErrorCode;
import com.ssawallafy.workalone_backend.domain.organization.exception.OrganizationException;
import com.ssawallafy.workalone_backend.domain.organization.repository.OranizationMappingRepository;
import com.ssawallafy.workalone_backend.domain.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OranizationMappingRepository organizationMappingRepository;
    private final MemberRepository memberRepository;

    public List<OrganizationDto> getOrganizations(Long memberId) {
        List<OrganizationMapping> organizationMappings = organizationMappingRepository.findByMemberId(memberId);

        List<OrganizationDto> organizationDtos = new ArrayList<>();
        for (OrganizationMapping organizationMapping : organizationMappings) {
            Organization organization = organizationRepository.findById(organizationMapping.getOrganization().getId())
                    .orElseThrow(() -> new OrganizationException(ErrorCode.ORGANIZATION_NOT_FOUND));
            organizationDtos.add(OrganizationDto.of(organization));
        }
        return organizationDtos;
    }

    @Transactional
    public void createOrganization(Long memberId, OrganizationReqDto organizationReqDto) {
        Organization organization = Organization.builder()
                .name(organizationReqDto.getName())
                .build();

        //TODO: 건강 담당자가 다루고 있는 모임 중 동일한 이름의 모임이 없도록 해야할 것 같음
        organizationRepository.save(organization);

        //TODO: throw 설정에 대해 이야기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        OrganizationMapping organizationMapping = OrganizationMapping.builder()
                .organization(organization)
                .member(member)
                .build();

        organizationMappingRepository.save(organizationMapping);
    }
}
