package com.ssawallafy.workalone_backend.domain.organization.controller;

import com.ssawallafy.workalone_backend.domain.organization.dto.request.OrganizationReqDto;
import com.ssawallafy.workalone_backend.domain.organization.dto.response.OrganizationDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationDocs {
    @Operation(summary = "모임 정보 조회", description = "건강담당자가 담당하고 있는 모임 정보를 반환합니다.")
    public ResponseEntity<List<OrganizationDto>> getOrganization();

    @Operation(summary = "모임 생성", description = "건강담당자가 모임을 개설합니다.")
    public ResponseEntity<?> createOrganization(OrganizationReqDto organizationReqDto);
}
