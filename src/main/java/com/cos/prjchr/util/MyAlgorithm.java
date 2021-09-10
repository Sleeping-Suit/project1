// ENUM = 열거형 type
package com.cos.prjchr.util;

import lombok.Getter;

// Getter 거는 것 까먹지 말 것
@Getter
public enum MyAlgorithm {
	SHA256("SHA-256"), SHA512("SHA-512");
	
	// 내가 키값을 괄호안의 값으로 변경해서 쓰고싶은 경우
	String type;
	
	private MyAlgorithm(String type) {
		this.type = type;
	}
	// ---------------------------------------------------
}
