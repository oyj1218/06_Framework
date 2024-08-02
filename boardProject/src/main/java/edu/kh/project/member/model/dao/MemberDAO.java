package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.dto.Member;

@Repository // Persistence Layer, 영속성 관련 클래스
			// (DB 관련 클래스, 파일) + Bean 등록(== S
public class MemberDAO {

	// SqlSession Template (마이바티스 객체) DI
	// 의존성 주입하기 : @Autowired 사용하기
	// 등록된 SqlSessionTemplate 타입의 Bean 주입
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/** 로그인 DAO
	 * @param inputMember
	 * @return 회원 정보 or null
	 */
	public Member login(Member inputMember) {
		
		
		// 항상 마이바티스(JDBC랑 비슷한거)를 이용할 예정
		// 마이바티스를 이용해서 1행 조회(selectOne)
		
		// sqlSession.selectOne("namespace값.id값", 전달할 값);
		// -> namespace가 일치하는 Mapper에서 id가 일치하는 SQL구문을 수행 후 결과를 1행(dto) 반환
		// return sqlSession.selectOne("memberMapper.id값", inputMember);
		
		
		//******************* Test *****************************
		//******************************************************

		
//		Member member=sqlSession.selectOne("memberMapper.login", inputMember);
		
		
		//******************* Test *****************************
//		System.out.println("inputMember(sqlSession후) : "+ member);
		//******************************************************
		System.out.println("dao :: " + inputMember);
		
//		return member;
		return sqlSession.selectOne("memberMapper.login", inputMember);
		
	}
	

}
