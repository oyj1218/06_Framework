package edu.kh.project.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.dto.Member;

@Repository // DB 연결 의미 + bean 등록
public class AjaxDAO {

	@Autowired // bean 중에서 타입이 같은 객체를 DI
	private SqlSessionTemplate sqlSession;

	/** 이메일로 닉네임 조회
	 * @param email
	 * @return nickname
	 */
	public String selectNickname(String email) {
		return sqlSession.selectOne("ajaxMapper.selectNickname", email);
	}

	/** 닉네임으로 전화번호 조회
	 * @param nickname
	 * @return tel
	 */
	public String selectMemberTel(String nickname) {
		return sqlSession.selectOne("ajaxMapper.selectMemberTel", nickname);
	}

	/**
	 * 이메일 중복 조회
	 * @param email
	 * @return result
	 */
	public int dupCheckEmail(String email) {
		return sqlSession.selectOne("ajaxMapper.dupCheckEmail", email);
	}

	/**
	 * 닉네임 중복 조회
	 * @param nickname
	 * @return result
	 */
	public int dupCheckNickname(String nickname) {
		return sqlSession.selectOne("ajaxMapper.dupCheckNickname", nickname);
	}

	/**
	 * 이메일로 회원 정보 조회
	 * @param email
	 * @return member
	 */
	public Member selectMember(String email) {
		return sqlSession.selectOne("ajaxMapper.selectMember", email);
	}

	/**
	 * 이메일 일부로 회원 정보 조회
	 * @param input
	 * @return memberList
	 */
	public List<Member> selectMemberList(String input) {
		return sqlSession.selectList("ajaxMapper.selectMemberList", input);
	}
}