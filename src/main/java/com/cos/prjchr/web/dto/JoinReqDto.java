package com.cos.prjchr.web.dto;

import com.cos.prjchr.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinReqDto {
	private String username;
	private String password;
	private String email;

	public User toEntity() {
		User user = new User();
		user.setId(1);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}
}
