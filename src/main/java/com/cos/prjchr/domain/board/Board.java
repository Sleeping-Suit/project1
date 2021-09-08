package com.cos.prjchr.domain.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
		private int id;
		private String title;
		private String content;

		@JoinColumn(name="userId")
		@ManyToOne
		private User user;
	}

