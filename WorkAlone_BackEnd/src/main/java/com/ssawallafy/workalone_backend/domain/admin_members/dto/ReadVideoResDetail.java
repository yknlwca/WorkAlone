package com.ssawallafy.workalone_backend.domain.admin_members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReadVideoResDetail {

	private LocalDate date;

	private String title;

	private String objectUrl;

}
