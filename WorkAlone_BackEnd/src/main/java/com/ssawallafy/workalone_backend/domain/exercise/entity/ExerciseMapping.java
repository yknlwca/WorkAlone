package com.ssawallafy.workalone_backend.domain.exercise.entity;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;

@Entity
@Getter
public class ExerciseMapping {

	@EmbeddedId
	private ExerciseMappingId id;

	@ManyToOne
	@MapsId("group_id")
	@JoinColumn(name = "group_id")
	private ExerciseGroup exerciseGroup;

	@ManyToOne
	@MapsId("member_id")
	@JoinColumn(name = "member_id")
	private Member member;

}
