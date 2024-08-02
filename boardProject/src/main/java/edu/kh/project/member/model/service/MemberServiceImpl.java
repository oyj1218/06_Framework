package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.project.member.model.dao.MemberDAO;
import edu.kh.project.member.model.dto.Member;

@Service // Service Layer
		 // 비즈니스 로직(데이터 가공, dao 호출, 트랜잭션 제어) 처리하는 클래스임을 명시
		 // + Bean으로 등록하는 어노테이션
public class MemberServiceImpl implements MemberService{
	
	// private MemberDAO dao = new MemberDAO();
	// 원래는 이렇게 썼는데 이렇게 안 쓰고 이미 객체로 만들어둔 거 있으면 @Autowired 사용
	
	// @Autowired : 작성된 필드와 Bean으로 등록된 객체 중 타입이 일치하는 Bean을 해당 필드에 자동으로 주입하는 어노테이션
	// == DI(Dependency Injection, 의존성 주입)
	// -> 객체를 직접 만들지 않고, Spring이 만든 객체를 주입함(Spring에 의존)
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired // bean으로 등록된 객체 중 타입이 일치하는 Bean을 DI(의존성 주입)한다
	private BCryptPasswordEncoder bcrypt;
	
	@Override
	public Member login(Member inputMember) {
		
		// 암호화 추가 예정
		// System.out.println("암호화 확인 : " + bcrypt.encode(inputMember.getMemberPw()));
		
		// bcrypt 암호화는 salt가 추가되기 때문에
		// 계속 비밀번호가 바뀌게 되어서 DB에서 비교 불가능!!
		// -> 별도로 제공해주는 matches(평문, 암호문)을 이용해서 비교
		
		
		// dao 메소드 호출
		Member loginMember = dao.login(inputMember);
		
		if(loginMember != null) { // 이메일이 일치하는 회원이 조회된 경우
			
			// 입력한 pw와 암호화된 pw가 같은지 확인
			if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) { // 같은 경우
				
				// 일치하는 지 확인했으면 제거해줘야함, 왜냐하면 select문에서 비밀번호 포함시켜서
				// 이 작업을 안 해주면 비밀번호도 회원한테 넘어가게 됨
				// 비밀번호를 유지하지 않기 위해서 로그인 정보에서 제거
				loginMember.setMemberPw(null);
				
				
			} else { // 다를 경우
				loginMember = null; // 로그인 실패한 것 처럼 만들어준다
				
			}
		}
		
		return loginMember;
	}
	

}
