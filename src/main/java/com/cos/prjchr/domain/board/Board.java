package com.cos.prjchr.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.cos.prjchr.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
	
	public class Board {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;					// PK 자동증가번호
		@Column(nullable = false, length = 50)
		private String title;			// 아이디
		@Lob
		private String content;

		@JoinColumn(name="userId")
		@ManyToOne(fetch = FetchType.EAGER)
		private User user;
	}

