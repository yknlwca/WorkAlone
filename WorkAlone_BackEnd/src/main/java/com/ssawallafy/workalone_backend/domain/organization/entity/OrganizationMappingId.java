package com.ssawallafy.workalone_backend.domain.organization.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganizationMappingId implements Serializable {

	private Long organization_id;

	private Long member_id;

	@Builder
	public OrganizationMappingId(Long organization_id, Long member_id) {
		this.organization_id = organization_id;
		this.member_id = member_id;
	}
}
