package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dao.CommentDAO;
import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.common.utility.Util;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO dao;

	// 댓글 목록 조회
	@Override
	public List<Comment> selectComment(int boardNo) {
		return dao.selectComment(boardNo);
	}

	// 댓글 생성
	@Transactional(rollbackFor = Exception.class) // DML 사용
	@Override
	public int insertComment(Comment comment) {
		
		// XSS 방지 처리
		comment.setCommentContent(Util.XSSHandling(comment.getCommentContent()));
		// 생성할 때 xss처리 꼭 해주기(for 보안)
		// board.setBoardContent(Util.XSSHandling(board.getBoardContent()));

		return dao.insertComment(comment);
	}

	// 댓글 삭제
	@Transactional(rollbackFor = Exception.class) // DML 사용
	@Override
	public int deleteComment(int commentNo) {
		return dao.deleteComment(commentNo);
	}

	// 댓글 수정
	@Transactional(rollbackFor = Exception.class) // DML 사용
	@Override
	public int updateComment(Comment comment) {
		comment.setCommentContent(Util.XSSHandling(comment.getCommentContent()));
		
		return dao.updateComment(comment);
	}
	
	

}
