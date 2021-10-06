package com.cos.prjchr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.domain.user.UserRepository;
import com.cos.prjchr.handler.ex.MyAsyncNotFoundException;
import com.cos.prjchr.handler.ex.MyNotFoundException;
import com.cos.prjchr.util.MyAlgorithm;
import com.cos.prjchr.util.SHA;
import com.cos.prjchr.web.dto.JoinReqDto;
import com.cos.prjchr.web.dto.LoginReqDto;
import com.cos.prjchr.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	// 이건 하나의 서비스(핵심로직)인가? (principal 값 변경, update치고, 세션값 변경(x))

	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 회원수정(User principal, UserUpdateDto dto) {
		User userEntity = userRepository.findById(principal.getId())
				.orElseThrow(()-> new MyAsyncNotFoundException("회원정보를 찾을 수 없습니다."));
		userEntity.setEmail(dto.getEmail());
	} // 더티 체킹

	public User 로그인(LoginReqDto dto) {
		return userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public void 회원가입(JoinReqDto dto) {
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		dto.setPassword(encPassword);
		userRepository.save(dto.toEntity());
	}
}
