package com.cos.prjchr.test;

import org.junit.jupiter.api.Test;

import com.cos.prjchr.domain.board.Board;

public class BoardControllerTest {

	@Test
	public void 익셉션테스트() {
		// try catch는 catch에서 처리 하는 것
		try {
			Board b = null;
			System.out.println(b.getContent());
		} catch (Exception e) {
			System.out.println("오류가 났어요");
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void 익셉션테스트2() throws Exception {
		// throw는 '익셉션테스트2' 값을 호출하는 곳으로 던져주는 것
		throw new Exception();
	}
	
}
