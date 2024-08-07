package edu.kh.project.member.model.service;

public interface AjaxService {
	
	/** 이메일로 닉네임 조회하기
	 * @param email
	 * @return nickname
	 */
	String selectNickname(String email);

	/**
	 * 닉네임으로 전화번호 조회하기
	 * @param nickname
	 * @return
	 */
	String selectMemberforTel(String nickname);
}