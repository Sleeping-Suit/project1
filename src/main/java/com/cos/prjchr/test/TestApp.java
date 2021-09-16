package com.cos.prjchr.test;

interface Callback {		// 인터페이스는 추상메서드 = 몸체X => 스택X => 구체적인 행위가 없다.
	void hello();
}

// A a = New A();
// Callback a = new A();
class A implements Callback {
	
	@Override
	public void hello () {
		System.out.println("Hello");
	}
}

public class TestApp {
	void speak(Callback c) {
		c.hello();
	}

	public static void main(String[] args) {
		TestApp t = new TestApp();
/*
		t.speak(new Callback() {
			
			@Override
			public void hello() {
				// 함수를 구현해 주어야 메모리에 뜬다.
				
			}
		});
*/
	}
	
}
