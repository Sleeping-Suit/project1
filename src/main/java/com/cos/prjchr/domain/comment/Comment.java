package com.cos.prjchr.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cos.prjchr.domain.board.Board;
import com.cos.prjchr.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //PK (자동증가 번호)

	@Column(nullable = false)
	private String content;

	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

	@JoinColumn(name = "boardId")
	@ManyToOne
	private Board board;	// 2

}

