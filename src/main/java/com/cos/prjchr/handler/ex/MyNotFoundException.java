// 오류의 카테고리화
package com.cos.prjchr.handler.ex;

/**
 * 
 * @author Hyeon 2021.09.16
 * 1. id를 못찾았을 때 사용
 * 
 */

public class MyNotFoundException extends RuntimeException {
	
	public MyNotFoundException(String msg) {
		super(msg);
	}

}
