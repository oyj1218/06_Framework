package edu.kh.project.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.member.model.dto.Member;


// @RequestMapping : 요청 주소에 맞는 클래스/메소드 연결
					 // 메소드 위에, 클래스 위에 상관없이 작성할 수 있다

// @RequestMapping("요청주소")
// -> Get / POST 구분 X(모두 받음, 주소만 맞으면 연결)

// @RequestMapping(value="요청주소", method=RequestMethod.POST)
// -> Get / POST 방식을 구분
// 구분해야 하면 method를 써줘야함


@Controller
@RequestMapping("/member") // 공통된 주소 앞부분 작성
						// member로 시작하는 요청은 해당 컨트롤러에서 처리
public class MemberController {
	// 로그인 : /member/login
	// 로그아웃 : /member/logout
	
	// 로그인(/member/login), POST 방식 처리
	// class에 작성된 /member를 제외한 나머지 주소 부분을 value에 작성
	// @RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest req) {
		
		// 파라미터 전달 방법 1 : HttpServletRequest를 이용
		// -> Controller 메소드의 매개변수로 HttpServletRequest 작성
		
		String inputEmail = req.getParameter("inputEmail");
		System.out.println("inputEmail: " + inputEmail);
		
		// * redirect 방법 *
		// "redirect: 요청주소"
		return "redirect:/";
	}
	
	// @PostMapping
	// -> @RequestMapping의 자식으로 POST 방식 요청을 연결하는 어노테이션
	// @PostMapping("/login")
	// 오버로딩으로 파라미터 타입, 개수, 순서가 다르기에 사용가능하다
	public String login(/*@RequestParam("inputEmail")*/ String inputEmail, 
			/*@RequestParam("inputPw")*/ String inputPw) {
		
		// 파라미터 전달 방법 2 : @Requestparam 어노테이션 이용(+생략 방법)
		// - request객체를 이용한 파라미터 전달 어노테이션
		// - 매개변수 앞에 해당 어노테이션을 작성하면 매개변수에 값이 주입됨
		
		
		// ** 파라미터의 name 속성 값과 매개변수 명이 같으면
		// @RequestParam 생략 가능 !! **
		
		System.out.println("inputEmail : " + inputEmail);
		System.out.println("inputPw : " + inputPw);
		
		// 메인페이지 리다이렉트(재요청)
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(/*@ModelAttribute*/ Member inputMember) {
		
		// 파라미터 전달 방법 3 : @ModelAttribute를 이용한 방법
		
		// - DTO(또는 VO)와 같이 사용하는 어노테이션
		
		// - 전달받은 파라미터의 name 속성값이 같이 사용되는 DTO의 필드명과 같다면
		// 	 자동으로 setter를 호출해서 필드에 값을 세팅
		
		// @ModelAttribute 사용 시 주의사항 !!
		// - DTO에 기본 생성자와 setter가 필수로 존재해야 한다!!
		
		// @ModelAttribute 어노테이션 생략 가능하다!
		
		// ** @ModelAttribute를 이용해 필드에 값이 세팅된 객체를
		// "커맨드 객체"라고 한다.
		
		
		System.out.println(inputMember);
		
		return "redirect:/";
	}

}
