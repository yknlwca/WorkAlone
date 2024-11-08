package com.ssawallafy.workalone_backend.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssawallafy.workalone_backend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT m FROM Member m WHERE m.name = :name")
	Optional<Object> findByName(String name);
}
