package edu.kh.project.board.model.exception;

// 사용자 정의 예외를 만드는 법!
// -> Exception 관련 클래스를 상속 받으면 된다.

// tip. unchecked exception을 만들고 싶으면 
// 		RuntimeException을 상속 받아서 구현

// unchecked exception : 예외 처리 선택 -> 개발자 쪽에서 이뤄난 것(무조건 대비할 필요 없다)
// checked exception : 예외 처리 필수

// 예외 처리 : try-catch / throws

public class ImageDeleteException extends RuntimeException {
	
	public ImageDeleteException() {
		super("이미지 삭제 예외 발생");
	}
	
	public ImageDeleteException(String message) {
		super(message);
	}
}
