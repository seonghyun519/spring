package com.sparta.springsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    //OncePerRequestFilter기존 필터 상속 doFilterInternal 함수를 Override해서 사용
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getParameter("username"); //getParameter 사용시 클라이언트에서 넘어오는 파라미터 값을 가지고 올 수 있음./ 키 이름으로("username")
        String password = request.getParameter("password");

        System.out.println("CustomSecurityFilter/doFilterInternal 실행");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("request.getRequestURI() = " + request.getRequestURI());


        if(username != null && password  != null && (request.getRequestURI().equals("/api/user/login") || request.getRequestURI().equals("/api/test-secured"))){
            //getRequestURI 들어온 URI 확인 /username과 pwd가 null이 아닐 때 수행 /다른 요청은 확인하는데 어려움이 있어 제공자가 임의로 걸어둔거 크게 생각할 필요는 없음.(실습 확인용)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //DB에서 조회한 user의 name,pwd가 있는 user객체도 있으면서 user의 권한 GrantedAuthority타입으로 추상화해서 가져올 수 있음

            // 비밀번호 확인
            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
            }

            // 인증 객체 생성 및 등록
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());//위에서 검증이 끝났기 때문에 credentials에 pwd대신 null을 넣어줌
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request,response); //doFilter에 담아서 다음 Filter로 이동./예외 처리가 발생시 이전 Filter로 예외가 넘어감
    }
}