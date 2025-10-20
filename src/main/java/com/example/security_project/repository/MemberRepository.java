package com.example.security_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.security_project.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
    @Query("select m from Member m join fetch m.roles where m.email = :email")
    Member getWithRoles(@Param("email") String email);
}
