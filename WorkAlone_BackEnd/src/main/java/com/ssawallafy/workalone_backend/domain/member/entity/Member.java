package com.ssawallafy.workalone_backend.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private long id;

	private String name;

	private String nickname;

	private String email;

	private String phone_number;

	private Boolean gender;

	private Boolean is_trainer;

	private int height;

	private int weight;

	private Boolean is_recording;

	public void updateNickname(String newNickname) {
		this.nickname = newNickname;
	}
	public void updateHeight(int newHeight) { this.height = newHeight; }
	public void updateWeight(int newWeight) { this.weight = newWeight; }

	public void updateIsRecording(boolean newIsRecording) { this.is_recording = newIsRecording; }
}
