package com.cos.prjchr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.domain.user.UserRepository;
import com.cos.prjchr.web.dto.LoginReqDto;

@Controller
public class UserController {

	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/test/join")
	public void testJoin() {
		User user = new User();
		user.setUsername("tea");
		user.setPassword("1234");
		user.setEmail("tea@tea.tea");
		
		userRepository.save(user);
	}
	
	
	@GetMapping("/home")
	public String home() {
		return "home";
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
	public String login(LoginReqDto dto) {
		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());
		
		return "home";
	}
	
}
