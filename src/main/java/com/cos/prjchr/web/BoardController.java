package com.cos.prjchr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.prjchr.domain.comment.Comment;
import com.cos.prjchr.domain.user.User;
import com.cos.prjchr.handler.ex.MyAsyncNotFoundException;
import com.cos.prjchr.handler.ex.MyNotFoundException;
import com.cos.prjchr.service.BoardService;
import com.cos.prjchr.service.CommentService;
import com.cos.prjchr.util.Script;
import com.cos.prjchr.web.dto.BoardSaveReqDto;
import com.cos.prjchr.web.dto.CMRespDto;
import com.cos.prjchr.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // Final이 붙은 필드에 대한 생성자가 만들어 짐
@Controller // Component 스캔 (Spring) IoC
public class BoardController {

	// DI
	private final BoardService boardService;
	private final CommentService commentService;
	private final HttpSession session;

	@PostMapping("/api/board/{boardId}/comment")
	public String commentSave(@PathVariable int boardId, CommentSaveReqDto dto) {

		// 1. DTO로 데이터 받기

		// 2. Comment 객체 만들기 (빈객체 생성)

		// 3. Comment 객체에 값 추가하기 , id : X, content: DTO값, user: 세션값, board: boardId로
		// findById하세요
		User principal = (User) session.getAttribute("principal");

		// 4. save 하기
		commentService.댓글등록(boardId, dto, principal);
		return "redirect:/board/" + boardId;
	}

	@PutMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @RequestBody @Valid BoardSaveReqDto dto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}

		User principal = (User) session.getAttribute("principal");

		boardService.게시글수정(id, principal, dto);
		return new CMRespDto<>(1, "업데이트 성공", null);
	}

	@GetMapping("/api/board/{id}/updateForm")
	public String boardUpdateForm(@PathVariable int id, Model model) {

		// 게시글 정보를 가지고 가야함.
		model.addAttribute("boardEntity", boardService.게시글수정페이지이동(id));
		return "board/updateForm";
	}

	// DELETE FROM board WHERE id = ?
//	@DeleteMapping("/board/{id}")
////	public @ResponseBody String deleteById(@PathVariable int id) {
////	boardRepository.deleteById(id);
////	return "ok"; // @ResponseBody 데이터 리턴!! String = text/plain
////}
//	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {
//
//		// 인증이 된 사람만 함수 접근 가능!! (로그인 된 사람)
//		User principal = (User) session.getAttribute("principal");
//		if(principal == null) {
//			throw new MyAsyncNotFoundException("인증이 되지 않았습니다.");
//		}
//
//		// 권한이 있는 사람만 함수 접근 가능(principal.id == {id})
//		Board boardEntity = boardRepository.findById(id)
//			.orElseThrow(()-> new MyAsyncNotFoundException("해당글을 찾을 수 없습니다."));
//		if(principal.getId() != boardEntity.getUser().getId()) {
//			throw new MyAsyncNotFoundException("해당글을 삭제할 권한이 없습니다.");
//		}

//		try {
//			boardRepository.deleteById(id); // 오류 발생??? (id가 없으면) 
//		} catch (Exception e) {
//			throw new MyAsyncNotFoundException(id+"를 찾을 수 없어서 삭제할 수 없어요.");
//		}
//		return new CMRespDto<String>(1, "성공", null); // @ResponseBody 데이터 리턴!! String
//	}

//	
//	// UPDATE TABLE board SET title = ?, content =? WHERE id = ?
//	@PutMapping("/board/{id}")

	@DeleteMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {

		User principal = (User) session.getAttribute("principal");

		boardService.게시글삭제(id, principal);

		return new CMRespDto<String>(1, "성공", null); // @ResponseBody 데이터 리턴!! String
	}

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
//		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> new MyNotFoundException(id + " 못찾았어요")); 
//																		// 중괄호를 제거하면 무조건 리턴하겠다는 코드가 됨
		// {return new MyNotFoundException(id+" 못찾았어요");});
		
		
		// Board 객체에 존재하는 것 (Board(0), User(0), List<Comment>(x))
		model.addAttribute("boardEntity", boardService.게시글상세보기(id));
		return "board/detail";		// ViewResolver
	}

	@PostMapping("/api/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {

		// 공통 로직 시작 -----------------------------
		User principal = (User) session.getAttribute("principal");

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		// 공통 로직 끝 -------------------------------
		
		// 핵심 로직 시작 -----------------------------
		boardService.게시글등록(dto, principal);
		// 핵심 로직 끝 -------------------------------
		
		return Script.href("/", "글쓰기 성공");
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	// /board?page=2
	@GetMapping("/board")
	public String home(Model model, int page) {

		model.addAttribute("boardsEntity", boardService.게시글목록보기(page));
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
