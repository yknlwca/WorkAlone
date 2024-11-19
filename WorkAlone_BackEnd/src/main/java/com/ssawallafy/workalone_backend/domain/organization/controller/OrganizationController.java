package com.ssawallafy.workalone_backend.domain.organization.controller;

import com.ssawallafy.workalone_backend.domain.organization.dto.request.OrganizationReqDto;
import com.ssawallafy.workalone_backend.domain.organization.dto.response.OrganizationDto;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;
import com.ssawallafy.workalone_backend.domain.organization.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
@Tag(name = "Organization", description = "organization api")
public class OrganizationController implements OrganizationDocs {
    private final OrganizationService organizationService;
    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getOrganization() {
        Long memberId = 2L;
        //TODO: 관리자의 ID가 맞는지 확인하는 로직 필요
        List<OrganizationDto> organizationDtos = organizationService.getOrganizations(memberId);
        return ResponseEntity.ok().body(organizationDtos);
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationReqDto organizationReqDto) {
        Long memberId = 2L;
        //TODO: Header로 전달되는 Id를 바탕으로 사용자 검증
        organizationService.createOrganization(memberId, organizationReqDto);
        return ResponseEntity.ok().build();
    }
}
