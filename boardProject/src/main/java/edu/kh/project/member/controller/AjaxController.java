package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.member.model.service.AjaxService;

@Controller // 요청/응답 제어 + bean 등록
public class AjaxController {
	
	@Autowired // DI
	private AjaxService service;
	
	// 이메일로 닉네임 조회
	@GetMapping(value="/selectNickname", produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String selectNickname(String email) {
									// 쿼리스트링에 담긴 파라미터
		return service.selectNickname(email);
	}
	
	// 닉네임으로 전화번호 조회
	@GetMapping(value="/selectMemberforTel", produces="application/text; charset=UTF-8")
	@ResponseBody
	public String selectMemberforTel(String nickname) {
					// 쿼리스트링에 담긴 파라미터
		
		return service.selectMemberforTel(nickname);
		
		
	}
	
	
}	