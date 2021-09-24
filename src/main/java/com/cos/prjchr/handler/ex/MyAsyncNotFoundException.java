package com.cos.prjchr.handler.ex;

/**
 * 
 * @author Hyeon 2021.09.16
 * 1. id를 못찾았을 때 사용
 * 
 */
public class MyAsyncNotFoundException extends RuntimeException{

	public MyAsyncNotFoundException(String msg) {
		super(msg);
	}
}