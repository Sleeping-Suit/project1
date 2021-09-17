package com.cos.prjchr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.prjchr.domain.board.Board;
import com.cos.prjchr.domain.board.BoardRepository;
import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.handler.ex.MyNotFoundException;
import com.cos.prjchr.util.Script;
import com.cos.prjchr.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // Final이 붙은 필드에 대한 생성자가 만들어 짐
@Controller // Component 스캔 (Spring) IoC
public class BoardController {

	// DI
	private final BoardRepository boardRepository;
	private final HttpSession session;

	// DELETE FROM board WHERE id = ?
//	@DeleteMapping("/board/delete?id=1")
//	
//	// UPDATE TABLE board SET title = ?, content =? WHERE id = ?
//	@PutMapping("/board/{id}")
	
	// RestFul API 주소 설계 방식
	
	
	
	// 쿼리스트링, 패스var => 디비 where 에 걸리는 친구들!!
	// 1. 컨트롤러 선정, 2. Http Method 선정, 3. 받을 데이터가 있는지!! (body, 쿼리스트링, 패스var)
	// 4. 디비에 접근을 해야하면 Model 접근하기 orElse Model에 접근할 필요가 없다.
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		// select * from board where id = :id
		// 0. 로그인, 상세보기 까지의 코드
//		Board boardEntity =  boardRepository.findById(id).get();

		// 1. orElse는 값을 찾으면 Board가 리턴, 못찾으면 (괄호안 내용 리턴)
//		Board boardEntity =  boardRepository.findById(id)
//				.orElse(new Board(100, "글없어요", "글없어요", null));

		// 2. orElseThrow
//		Board boardEntity = boardRepository.findById(id).orElseThrow(new Supplier<MyNotFoundException>() {
//			@Override
//			public MyNotFoundException get() {
//				return new MyNotFoundException(id + "번을 찾을 수 없습니다.");
//			}
//		}); // 익셉션마다 다르게 처리하려면(어떻게 처리할지 모르니까) 인터페이스로 함수를 넘겨야한다

		// 람다식 사용 orElseThrow
		Board boardEntity = boardRepository.findById(id).
				orElseThrow(() -> new MyNotFoundException(id+" 못찾았어요"));	// 중괄호를 제거하면 무조건 리턴하겠다는 코드가 됨
							   // {return new MyNotFoundException(id+" 못찾았어요");});
		model.addAttribute("boardEntity", boardEntity);
		return "board/detail";
		}

	@PostMapping("/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {

		User principal = (User) session.getAttribute("principal");

		// 인증체크
		if (principal == null) { // 로그인 안됨
			return Script.href("/loginForm", "잘못된 접근입니다");
		}

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

		System.out.println(dto.getTitle());
		System.out.println(dto.getContent());
		// 10시 15분까지 -> BoardSaveReqDto 생성
		// Postman으로 테스트
		// 콘솔에 출력

//			User user = new User();
//			user.setId(3);
//			boardRepository.save(dto.toEntity(user));

		boardRepository.save(dto.toEntity(principal));
		return Script.href("/", "글쓰기 성공");
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	// /board?page=2
	@GetMapping("/board")
	public String home(Model model, int page) {

		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
		Page<Board> boardsEntity = boardRepository.findAll(pageRequest);
		model.addAttribute("boardsEntity", boardsEntity);
		// System.out.println(boardsEntity.get(0).getUser().getUsername());
		return "board/list";
	}
}

//	   @GetMapping({"/board"})
//	   public String home(Model model, Integer page) {
//
//		   // localhost:8080이 500 에러일 때 쓸 수 있는 1번째 방법
////		   if(page==null) {
////			   System.out.println("page값이 null입니다.");
////			   page = 0;
////		   }
//		   
//		   // 2번째 방법
//		    
//	      PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
//	      
//	      // Sort.by(Sort.Direction.DESC, "id")
//	      Page<Board> boardsEntity = 
//	            boardRepository.findAll(pageRequest);
//	      model.addAttribute("boardsEntity", boardsEntity);
//	      //System.out.println(boardsEntity.get(0).getUser().getUsername());
//	      return "board/list";
//	   }
//	}
