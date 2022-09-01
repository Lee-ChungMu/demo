package com.example.demo.security.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUserDetailService  implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername " + username);


        Optional<Member> result = memberRepository.findByEmail(username, false);

        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check User Email or from Social ");
        }

        Member member = result.get();

        log.info("-----------------------------");
        log.info(member);
        //Member를 UserDetails 타입으로 처리하기 위해 AuthMemberDTO로 변환
        AuthMemberDTO clubAuthMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                //권한은 Spring Security의 SimpleGrantedAuthority로 변환
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );

        clubAuthMember.setName(member.getName());
        clubAuthMember.setFromSocial(member.isFromSocial());

        return clubAuthMember;
    }
}
