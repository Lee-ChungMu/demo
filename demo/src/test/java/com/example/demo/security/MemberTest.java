package com.example.demo.security;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberRole;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        //1 - 80까지는 USER만 지정
        //81- 90까지는 USER,MANAGER
        //91- 100까지는 USER,MANAGER,ADMIN

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("user"+i+"@zerock.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .roleSet(new HashSet<MemberRole>())
                    .password(  passwordEncoder.encode("1111") )
                    .build();

            //default role
            member.addMemberRole(MemberRole.USER);

            if(i > 80){
                member.addMemberRole(MemberRole.MANAGER);
            }

            if(i > 90){
                member.addMemberRole(MemberRole.ADMIN);
            }

            repository.save(member);

        });

    }

    @Test
    public void testRead() {

        Optional<Member> result = repository.findByEmail("user95@zerock.org", false);

        Member member = result.get();

        System.out.println(member);

    }
}
