package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

	/**
	 * 닉네임으로 전화번호 조회
	 * @param inputNickname
	 * @return telephone
	 */
	public String selectMemberforTel(String nickname) {
		return sqlSession.selectOne("ajaxMapper.selectMemberforTel", nickname);
	}
}