package com.ssawallafy.workalone_backend.domain.exercise.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ExerciseMappingId implements Serializable {

	private long group_id;

	private long member_id;

}
