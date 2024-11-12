package com.ssawallafy.workalone_backend.domain.exercise.entity;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;
import com.ssawallafy.workalone_backend.domain.organization.entity.Organization;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseMapping {

	@EmbeddedId
	private final ExerciseMappingId id = new ExerciseMappingId();

	@ManyToOne
	@MapsId("group_id")
	@JoinColumn(name = "group_id")
	private ExerciseGroup exerciseGroup;

	@ManyToOne
	@MapsId("member_id")
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public ExerciseMapping(ExerciseGroup exerciseGroup, Member member) {
		this.exerciseGroup = exerciseGroup;
		this.member = member;
	}
}
