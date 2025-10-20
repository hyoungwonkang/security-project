package com.example.security_project.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.security_project.domain.Member;
import com.example.security_project.domain.MemberRole;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // h2안쓰고 mysql 데이터베이스 연동
@Transactional
public class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Rollback(false)
    void testSave() {
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Member member = Member.builder()
                .email("user" + i + "@gmail.com")
                .password(passwordEncoder.encode("1111"))
                .nickName("user" + i)
                .build();

            member.addRole(MemberRole.USER);
            if (i >= 5) {
                member.addRole(MemberRole.MANAGER);
            }
            if (i >= 8) {
                member.addRole(MemberRole.USER);
            }
            members.add(member);
        }
        memberRepository.saveAll(members);      
    }
    
    @Test
    void testGetWithRoles() {

        //given
        String email = "user6@gmail.com";

        //when
        Member member = memberRepository.getWithRoles(email);

        //then
        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getRoles().size()).isEqualTo(2);

        member.getRoles().forEach(role -> {
            System.out.println("role: " + role);
        });
    }
}
