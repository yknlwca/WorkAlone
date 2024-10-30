package com.ssawallafy.workalone_backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
