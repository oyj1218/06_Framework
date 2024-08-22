package edu.kh.project.board.model.service;

import java.util.List;

import edu.kh.project.board.model.dto.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return cList
	 */
	List<Comment> selectComment(int boardNo);

	/**
	 * 댓글 생성
	 * @param comment
	 * @return result
	 */
	int insertComment(Comment comment);

	/**
	 * 댓글 삭제
	 * @param commentNo
	 * @return result
	 */
	int deleteComment(int commentNo);

	/**
	 * 댓글 수정
	 * @param comment
	 * @return
	 */
	int updateComment(Comment comment);
	
	
	
	

}
