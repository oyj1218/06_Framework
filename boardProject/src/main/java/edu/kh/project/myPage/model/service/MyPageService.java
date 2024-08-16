package edu.kh.project.myPage.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

public interface MyPageService {

	int updateInfo(Member updateMember);

	
	/** 비밀번호 변경 서비스
	 * @param currentPw
	 * @param newPw
	 * @param memberNo
	 * @return result
	 */
	int changePw(String currentPw, String newPw, int memberNo);


	/**
	 * 회원 탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return
	 */
	int secession(String memberPw, int memberNo);

	/**
	 * 프로필 이미지 수정 서비스
	 * @param profileImage
	 * @param webPath
	 * @param filePath
	 * @param loginMember
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int updateProfile(MultipartFile profileImage, String webPath, String filePath, Member loginMember) throws IllegalStateException, IOException;


}