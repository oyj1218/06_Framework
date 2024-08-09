package edu.kh.project.member.model.service;

import java.util.List;

import edu.kh.project.member.model.dto.Member;

public interface AjaxService {
	
	/** 이메일로 닉네임 조회하기
	 * @param email
	 * @return nickname
	 */
	String selectNickname(String email);

	/** 닉네임으로 전화번호 조회
	 * @param nickname
	 * @return tel
	 */
	String selectMemberTel(String nickname);

	/** 이메일 중복 조회
	 * @param email
	 * @return result
	 */
	int dupCheckEmail(String email);

	/**
	 * 닉네임 중복 조회
	 * @param nickname
	 * @return result
	 */
	int dupCheckNickname(String nickname);

	/**
	 * 이메일로 회원 정보 조회
	 * @param email
	 * @return member
	 */
	Member selectMember(String email);

	
	/**
	 * 이메일 일부로 회원 정보 조회
	 * @param input
	 * @return memberList
	 */
	List<Member> selectMemberList(String input);


}