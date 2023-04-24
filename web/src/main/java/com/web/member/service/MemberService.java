package com.web.member.service;

import com.web.common.annotation.LogExecutionTime;
import com.web.common.entity.Member;
import com.web.member.dto.SignupRequestDto;
import com.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @LogExecutionTime
    public String signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByMemberId(signupRequestDto.getMemberId()).isPresent()){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        Member member = Member.builder()
                .memberId(signupRequestDto.getMemberId())
                .memberPassword(signupRequestDto.getMemberPassword())
                .build();
        memberRepository.save(member);
        return "회원가입 성공";
    }
    public String login(String memberId, String password) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isEmpty()) {
            throw new NotFoundException("아이디가 존재하지 않습니다.");
        }
        if (!member.get().getMemberPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return memberId + "님 로그인하였습니다.";
    }


}
