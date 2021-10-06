package com.cos.prjchr.test;

//import org.junit.jupiter.api.Test;
//
//import com.cos.prjchr.domain.board.Board;
//
//public class BoardControllerTest {
//
//	@Test
//	public void 익셉션테스트() {
//		// try catch는 catch에서 처리 하는 것
//		try {
//			Board b = null;
//			System.out.println(b.getContent());
//		} catch (Exception e) {
//			System.out.println("오류가 났어요");
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void 익셉션테스트2() throws Exception {
//		// throw는 '익셉션테스트2' 값을 호출하는 곳으로 던져주는 것
//		throw new Exception();
//	}
//
//}

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.prjchr.domain.board.Board;
import com.cos.prjchr.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardControllerTest {

	private final BoardRepository boardRepository;

	@GetMapping("/test/board/{id}")
	public Board detail(@PathVariable int id) {
		// 영속성컨텍스트 = Board(User 있음, List<Comment) 없음)
		Board boardEntity = boardRepository.findById(id).get();
		//System.out.println(boardEntity); // 객체 호출하면 자동으로 toString() 함수가 호출된다.
		return boardEntity;// MessageConverter
	}


}

