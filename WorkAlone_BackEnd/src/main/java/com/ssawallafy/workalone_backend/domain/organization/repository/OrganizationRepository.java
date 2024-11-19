package com.ssawallafy.workalone_backend.domain.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
