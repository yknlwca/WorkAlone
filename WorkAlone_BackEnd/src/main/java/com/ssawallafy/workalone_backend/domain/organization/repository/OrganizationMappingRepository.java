package com.ssawallafy.workalone_backend.domain.organization.repository;

import com.ssawallafy.workalone_backend.domain.organization.entity.OrganizationMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationMappingRepository extends JpaRepository<OrganizationMapping, Long> {
    List<OrganizationMapping> findByMemberId(Long memberId);
}
