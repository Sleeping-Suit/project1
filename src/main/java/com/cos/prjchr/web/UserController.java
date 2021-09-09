package com.cos.prjchr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.domain.user.UserRepository;
import com.cos.prjchr.util.Script;
import com.cos.prjchr.web.dto.JoinReqDto;
import com.cos.prjchr.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserRepository userRepository;
	private final HttpSession session;


	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@PostMapping("/login")
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : "+error.getField());
				System.out.println("메시지 : "+error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		// 1. username, password 받기
		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());
		// 2. DB -> 조회
		User userEntity =  userRepository.mLogin(dto.getUsername(), dto.getPassword());
		
		if(userEntity == null) {		// username,password 잘못 기입
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		}else {
			session.setAttribute("principal", userEntity);
			return Script.href("/", "로그인 성공");
		}
	}
	
	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) { // username=love&password=1234&email=love@nate.com
		
		// 1. 유효성 검사 실패 - 자바스크립트 응답(경고창, 뒤로가기)
		// 2. 정상 - 로그인 페이지

		// System.out.println("에러사이즈 : "+bindingResult.getFieldErrors().size());
		
		// ---------------------------------------------------------------------------
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : "+error.getField());
				System.out.println("메시지 : "+error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		//---------------------------------------------------------------------공통함수
		
		userRepository.save(dto.toEntity());
		return Script.href("/loginForm"); // 리다이렉션 (300)
	}
	
}
