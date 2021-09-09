package com.cos.prjchr.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.prjchr.domain.board.Board;
import com.cos.prjchr.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller		// Component 스캔 (Spring) IoC 
public class BoardController {

	   private final BoardRepository boardRepository;

	   @GetMapping({"/board"})
	   public String home(Model model, Integer page) {

		   // localhost:8080이 500 에러일 때 쓸 수 있는 1번째 방법
//		   if(page==null) {
//			   System.out.println("page값이 null입니다.");
//			   page = 0;
//		   }
		   
		   // 2번째 방법
		   
		   
	      PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
	      
	      // Sort.by(Sort.Direction.DESC, "id")
	      Page<Board> boardsEntity = 
	            boardRepository.findAll(pageRequest);
	      model.addAttribute("boardsEntity", boardsEntity);
	      //System.out.println(boardsEntity.get(0).getUser().getUsername());
	      return "board/list";
	   }
	}
