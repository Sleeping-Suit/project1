package com.cos.prjchr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class SHATest {

	@Test
	public String encrypt() {
		String salt = "코스";
		String rawPassword = "1234" + salt;

		// 1. SHA256 함수를 가진 클래스객체 가져오기
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256"); // AES, MD5는 뚫렸음
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 2. 비밀번호 1234 -> SHA256 던지기
		md.update(rawPassword.getBytes());

//		for(Byte b:rawPassword.getBytes()) {System.out.print(b);}

		System.out.println();

//		for(Byte b:md.digest()) {System.out.print(b);}	

		// 3. 암호화된 글자를 16진수로 변환(헥사코드)
		StringBuilder sb = new StringBuilder();
		for (Byte b : md.digest()) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
