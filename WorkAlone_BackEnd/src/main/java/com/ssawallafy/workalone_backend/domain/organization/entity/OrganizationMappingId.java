package com.ssawallafy.workalone_backend.domain.organization.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrganizationMappingId implements Serializable {

	private Long organization_id;

	private Long trainer_id;

}
