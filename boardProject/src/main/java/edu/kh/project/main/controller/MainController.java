package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller : 현재 클래스가 컨트롤러임을 명시 -> 요청, 응답 처리
// 					+ bean 등록

// instance : 클래스 -> 객체
// 			new 클래스명(); 객체 생성을 개발자가 직접 함

// IOC(Inversion of Control, 제어의 역전) :
// -> 프레임워크(Spring Container)가 객체를 생성하고 관리
// --> 이때 생성된 객체 == Bean

@Controller
public class MainController {
	
	// @RequestMapping("/") : 요청 주소가 "/"인 경우 해당 메소드와 연결
	@RequestMapping("/")
	public String mainFoward() {
		// main.jsp로 화면 전환
		// <beans:property name="prefix" value="/WEB-INF/views/" />
		// <beans:property name="suffix" value=".jsp" />
		
		// 원래 servlet에선 return "/WEB-INF/views/common/main.jsp";
		// Spring에서 forward 하는 법
		// -> webapp 폴더를 기준으로 요청 위임할 JSP 파일 경로를 리턴한다.
		// 단, servlet-context.xml에 작성된 prefix, suffix 부분을 제외하고 작성한다.
		// -> prefix + 리턴 값 + suffix로 경로 완성됨!
		// ** View Resolver **
		
		return "common/main";
	}
}
