package com.ssawallafy.workalone_backend.domain.organization.entity;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;

@Entity
@Getter
public class OrganizationMapping {

	@EmbeddedId
	private OrganizationMappingId id;

	@ManyToOne
	@MapsId("organization_id") // OrganizationMappingId의 organizationId와 매핑
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToOne
	@MapsId("member_id") // OrganizationMappingId의 memberId와 매핑
	@JoinColumn(name = "member_id")
	private Member member;
}
