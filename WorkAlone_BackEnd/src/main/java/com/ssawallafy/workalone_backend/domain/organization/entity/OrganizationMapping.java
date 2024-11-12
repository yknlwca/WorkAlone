package com.ssawallafy.workalone_backend.domain.organization.entity;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrganizationMapping {

	@EmbeddedId
	private OrganizationMappingId id = new OrganizationMappingId();

	@ManyToOne
	@MapsId("organization_id") // OrganizationMappingId의 organizationId와 매핑
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToOne
	@MapsId("member_id") // OrganizationMappingId의 memberId와 매핑
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public OrganizationMapping(Organization organization, Member member) {
		this.organization = organization;
		this.member = member;
	}
}
