package edu.kh.project.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(Exception.class)
	public String exceptionHanlder(Exception e, Model model) {
		
		// Exception e : 예외 정보를 담고 있는 객체
		// Model model : 데이터 전달용 객체(request scope)
							// request scope가 기본
		e.printStackTrace();
		
		model.addAttribute("e", e);
		
		// forward 진행
		// -> View Resolver가 prefix, suffix를 붙여서 jsp 경로 만든다!
		return "common/error";
	}

}
