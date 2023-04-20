package com.web.member.service;

import com.web.common.entity.Member;
import com.web.member.dto.SignupRequestDto;
import com.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public String signup(SignupRequestDto signupRequestDto) {
        if (memberRepository.findByMemberId(signupRequestDto.getMemberId()).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        Member member = Member.builder()
                .memberId(signupRequestDto.getMemberId())
                .memberPassword(signupRequestDto.getMemberPassword())
                .build();
        memberRepository.save(member);
        return "회원가입 성공";
    }


}
