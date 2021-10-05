package com.cos.prjchr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.handler.ex.MyAsyncNotFoundException;
import com.cos.prjchr.service.UserService;
import com.cos.prjchr.util.Script;
import com.cos.prjchr.web.dto.CMRespDto;
import com.cos.prjchr.web.dto.JoinReqDto;
import com.cos.prjchr.web.dto.LoginReqDto;
import com.cos.prjchr.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	private final HttpSession session;

	@PostMapping("/user/{id}")
	public @ResponseBody CMRespDto<String>update(@PathVariable int id, @Valid UserUpdateDto dto, BindingResult bindingResult) {
		 // 유효성
	      if (bindingResult.hasErrors()) {
	         Map<String, String> errorMap = new HashMap<>();
	         for (FieldError error : bindingResult.getFieldErrors()) {
	            errorMap.put(error.getField(), error.getDefaultMessage());
	         }
	         throw new MyAsyncNotFoundException(errorMap.toString());
	      }
	      
	      // 인증
	      User principal = (User) session.getAttribute("principal");
	      if (principal == null) { // 로그인 안됨
	         throw new MyAsyncNotFoundException("인증이 되지 않았습니다");
	      }
	      
	      // 권한
	      if(principal.getId()!=id) {
	    	  throw new MyAsyncNotFoundException("회원정보를 수정할 권한이 없습니다.");
	      }
	      
	      // 핵심로직
	      session.setAttribute("principal", principal);
	      userService.회원수정(principal, dto);
	      
	      return new CMRespDto<>(1, "성공", null);
	}
	
	@GetMapping("/user/{id}")
	public String userinfo(@PathVariable int id) {
		// 기본은 userRepository.findById(id) DB에서 가져와야 함.
		// 편법은 세션값을 가져올 수도 있다.
		
		return "user/updateForm";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); // 세션 무효화 (jsessionId에 있는 값을 비우는 것)
		return "redirect:/"; // 게시글 목록 화면에 데이터가 있을까요?
	}

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

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " + error.getField());
				System.out.println("메시지 : " + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

		// 1. username, password 받기
		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());

		// 2. DB -> 조회
		User userEntity = userService.로그인(dto);

		if (userEntity == null) { // username,password 잘못 기입
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		} else {
			// 세션 날라가는 조건: 1. session.invailidate() - 로그아웃, 2. 브라우저를 닫으면 날라감
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
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " + error.getField());
				System.out.println("메시지 : " + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		// ---------------------------------------------------------------------공통함수


		userService.회원가입(dto);
		return Script.href("/loginForm"); // 리다이렉션 (300)
	}

}
