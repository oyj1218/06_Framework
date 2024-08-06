package edu.kh.project.myPage.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.MyPageService;

@SessionAttributes({"loginMember"})

//1) Model에 세팅된 값의 key와 {}에 작성된 값이 일치하면 session scope로 이동
//2) Session으로 올려둔 값은 해당 클래스에서 얻어와 사용을 가능하게 함
//		-> @SessionAttribute(key)로 사용

@RequestMapping("/myPage") // /myPage로 시작하는 요청을 모두 받음
@Controller // 요청/응답 제어 클래스 + Bean 등록
public class MyPageController {
	
	@Autowired // MyPageService의 자식 MyPageServiceImple 의존성 주입(DI)
	private MyPageService service;
	
	// 내 정보 페이지로 이동
	@GetMapping("/info")
	public String info() {
		// ViewResolver 설정 -> servlet-context.xml
		return "myPage/myPage-info";
	}
	
	// 프로필 페이지 이동
	@GetMapping("/profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	// 비밀번호 변경 페이지 이동
	@GetMapping("/changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	
	// 회원 탈퇴 페이지 이동
	@GetMapping("/secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	// 회원 정보 수정
	// <form action="info" method="POST" name="myPageFrm">
	// <button class="myPage-submit">수정하기</button>
	
	// 만약 내가 현재 있는 위치에서 http://localhost/myPage/info
	@PostMapping("/info")
	public String info(Member updateMember, String[] memberAddress
			, @SessionAttribute("loginMember") Member loginMember
			, RedirectAttributes ra) {
		// --------- 매개변수 설명 ----------
		// Member updateMember : 수장할 닉네임, 전화번호가 담긴 커맨드 객체
		// String[] memberAddress : name="memberAddress"인 input 3개의 값(주소)
		
		// @SessionAttribute("loginMember") Member loginMember
		// : Session에서 얻어온 "loginMember"에 해당하는 객체를 매개변수 Member loginMember에 저장
		
		// RedirectAttributes ra : 리다이렉트 시 데이터를 request scope로 전달하는 객체
		
		// -------------------------------
		
		// 주소 하나로 합치기(1^^^2^^^3^^^)
		if(updateMember.getMemberAddress().equals(",,")) {
			updateMember.setMemberAddress(null);

		} else {

			// String.join("구분자", String[])
			// 배열의 요소를 하나의 문자열로 변경
			// 단, 요소 사이에 "구분자" 추가
			String addr = String.join("^^^", memberAddress);
			updateMember.setMemberAddress(addr);
		}
		
		// 로그인한 회원 번호를 updateMember에 추가
		updateMember.setMemberNo(loginMember.getMemberNo());
		
		
		// DB에 회원 정보 수정 서비스 호출
		int result = service.updateInfo(updateMember);
		
		String message = null;
		// 수정 성공 시 "회원 정보가 수정되었습니다"
		if(result > 0) {
			
			// 학원 사람들과 토론 : 
			// model.addAttribute("loginMember", updateMember);
			
			// 로그인 멤버는 session에 있음
			loginMember.setMemberNickname(updateMember.getMemberNickname());
			loginMember.setMemberTel(updateMember.getMemberTel());
			loginMember.setMemberAddress(updateMember.getMemberAddress());
			
			message = loginMember.getMemberNickname() + " 회원 정보 수정에 성공했습니다";
		}
		
		// 수정 실패 시 "회원 정보 수정 실패"
		else {
			message = loginMember.getMemberNickname() + "회원 정보 수정에 실패했습니다";
		}
		// 내정보 수정 화면으로 돌아가기
		// 리다이렉트 시 session에 잠깐 올라갔다 내려오도록 세팅
		// 재요청이라서 사라지니깐 RedirectAttributes ra 필요하다
		ra.addFlashAttribute("message", message);
		return "redirect:info";
	}
	
	// myPage-changePw : <form action="changePw" method="POST" name="myPageFrm" id="changePwFrm">
	@PostMapping("/changePw")
	
	// name 속성 같으니깐 @RequestParam 가져올 수 있다
	public String changePw(String currentPw, String newPw
			
			// 로그인한 회원 정보는 session에 있으니깐 @SessionAttribute에서 가져온다
			, @SessionAttribute("loginMember") Member loginMember
			, RedirectAttributes ra) {
		
		// 로그인한 회원 번호
		int memberNo = loginMember.getMemberNo();
		
		// 비밀번호 변경 서비스 호출
		int result = service.changePw(currentPw, newPw, memberNo);
		
		String path = "redirect:";
		String message = null;
		if(result > 0) {
			// 비밀번호 변경 성공 시 "비밀번호 변경 성공" 출력 후 내정보페이지로
			message = loginMember.getMemberNickname() + "비밀번호 변경에 성공했습니다";
			path += "info";
			// 절대경로 : path += "/myPage/info";
		}
		else {
			// 비밀번호 변경 실패 시 : "현재 비밀번호가 일치하지 않습니다" 출력 후 비밀번호 변경페이지로
			message = loginMember.getMemberNickname() + "현재 비밀번호가 일치하지 않습니다";
			path += "changePw";
			
		}
		ra.addFlashAttribute("message", message);
		return path;
	}
	
	// 회원 탈퇴
	// myPage-secession : <form action="secession" method="POST" name="myPageFrm">
	@PostMapping("/secession")
	public String secession(String memberPw
			, @SessionAttribute("loginMember") Member loginMember
			, RedirectAttributes ra
			, SessionStatus status
			, HttpServletResponse resp) {
		
		// 1. 로그인한 회원의 회원 번호 얻어오기
		int memberNo = loginMember.getMemberNo();
		
		// 2. 회원 탈퇴 서비스 호출
		int result = service.secession(memberPw, memberNo);
		// 비밀번호 일치하면 MEMBER_DEL_FL -> 'Y'로 바꾸고 1 반환
		// 비밀번호 일치하지 않으면 -> 0 반환
		
		
		// 3. 탈퇴 성공시 
		// message : 탈퇴되었습니다.
		// 로그아웃
		// 메인페이지로 리다이렉트
		// 쿠키 삭제
		String path = "redirect:";
		String message = null;
		if(result > 0) {
			// 비밀번호 변경 성공 시 "비밀번호 변경 성공" 출력 후 내정보페이지로
			// message = loginMember.getMemberNickname() + "탈퇴되었습니다.";
			message = "탈퇴되었습니다.";
			path += "/";
			
			status.setComplete();
			
			Cookie cookie = new Cookie("saveId", "");
			cookie.setMaxAge(0); 
			cookie.setPath("/"); 
			resp.addCookie(cookie);
		}
		else {
			// 4. 탈퇴 실패 시
			// message : 현재 비밀번호가 일치하지 않습니다
			// 회원 탈퇴 페이지로 리다이렉트
			// message = loginMember.getMemberNickname() + "현재 비밀번호가 일치하지 않습니다";
			message = "현재 비밀번호가 일치하지 않습니다";
			path += "secession";
			
		}
		ra.addFlashAttribute("message", message);
		return path;
	}
		
		
}
	

