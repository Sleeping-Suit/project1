package com.cos.prjchr.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.prjchr.handler.ex.MyNotFoundException;
import com.cos.prjchr.util.Script;

// @ControllerAdvice는 1. 익셉션핸들링, 2. 컨트롤러의 역할까지 함
@ControllerAdvice
public class GlobalExceptionHandler {

	// 익셉션 종류별로 파일, 데이터, 뒤로가기, 메인페이지로 가기
	
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류 터졌어 : " + e.getMessage());
		return Script.href("/", "게시글 id를 찾을 수 없습니다.");
	}
}
