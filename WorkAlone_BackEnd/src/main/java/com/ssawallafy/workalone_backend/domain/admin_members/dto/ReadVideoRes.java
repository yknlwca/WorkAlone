package com.ssawallafy.workalone_backend.domain.admin_members.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReadVideoRes {
	List<ReadVideoResDetail> videoList;
}
