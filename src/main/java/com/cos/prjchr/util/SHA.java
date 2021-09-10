package com.cos.prjchr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	// encrypt: 암호화 // add throw는 함수 전체에 거는것
	public static String encrypt(String rawPassword, MyAlgorithm algorithm) {

		// 1. SHA256 함수를 가진 클래스 객체 가져오기
		// 내가 new를 못해서 new가 되어있는걸 재사용
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm.getType());	// SHA-256, 512만 기억할 것
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 2. 비밀번호 1234를 SHA256에 던지기
		// 바이트로 변경
		md.update(rawPassword.getBytes());

		StringBuilder sb = new StringBuilder();

		// 3. 암호화된 글자를 16진수로 변환(헥사코드)
		for (Byte c : md.digest()) {
			sb.append(String.format("%02x", c));
		}
		return sb.toString();

	}
}
