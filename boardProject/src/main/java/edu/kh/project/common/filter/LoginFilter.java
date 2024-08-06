package edu.kh.project.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.kh.project.member.model.dto.Member;

// Filter : 클라이언트 요청/응답을 걸러내거나 첨가하는 클래스
// client -> Filter -> Dispatcher Servlet
// 돌아올 때도 사용할 순 있지만 경우가 적기에 할 수만 있는걸로 기억하자

// @WebFilter : 해당 클래스를 필터로 등록하고 지정된 주소로 요청이 올 때 마다 동작
@WebFilter(filterName = "loginFilter", urlPatterns= "/myPage/*")
public class LoginFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
		// 서버가 켜질 때, 필터 코드가 변경 되었을 때 필터가 생성됨
		// -> 생성 시 초기화 용도로 사용하는 메소드
		System.out.println("--- 르그인 필터 생성 ---");
	}
	

	public void destroy() {
		// 필터 코드가 변경되었을 때
		// 변경 이전 필터를 파괴하는 메소드
		System.out.println("--- 이전 로그인 필터 파괴 ---");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// 로그인을 했는지 안 했는지 상태를 보고 메인페이지로 리다이렉트로 보내고 싶음
		
		// 1) 우리가 필요한 건 HttpServletRequest, 이건 ServletRequest의 자식이니깐 다운캐스팅을 해줘야함
		// ServletRequest, ServletResonse 다운캐스팅
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		// 2) HttpServletRequest를 이용해서 Httpsession 얻어오기
		HttpSession session = req.getSession();
		
		// 3) session에서 "loginMember" key를 가진 속성을 얻어와 null인 경우 메인 페이지로 redirect
			
			// (1) session에서 loginMember key 가져오기 : session.getAttribute("loginMember")
		
			// 만약에 session에서 로그인 멤버가 null이면
		if(session.getAttribute("loginMember") == null) {
			resp.sendRedirect("/");
		}
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember.getAuthority() == 1) {
			// 1이면 회원
			// 2이면 관리자
			// loginMember.getAuthority() == 2
			// resp.sendRedirect("/"); : 따로 관리자 페이지를 만들었다면
		}
		
		// 4) 로그인 상태인 경우 다음 필터 또는 DispatcherServlet으로 전달
		chain.doFilter(request,  response);
	}


	

}
