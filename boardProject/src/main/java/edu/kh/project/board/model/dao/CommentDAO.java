package edu.kh.project.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.dto.Comment;

@Repository
public class CommentDAO {

	@Autowired // bean 중에서 타입이 같은 객체를 DI
	private SqlSessionTemplate sqlSession;

	// 댓글 목록 조회
	public List<Comment> selectComment(int boardNo) {
		
		// board-mapper.xml에 작성된 selectCommentList 사용
		return sqlSession.selectList("boardMapper.selectCommentList", boardNo);
	}

	// 댓글 생성
	public int insertComment(Comment comment) {
		return sqlSession.insert("commentMapper.insertComment", comment);
	}

	// 댓글 삭제
	public int deleteComment(int commentNo) {
		return sqlSession.update("commentMapper.deleteComment", commentNo);
	}

	// 댓글 수정

	public int updateComment(Comment comment) {
		return sqlSession.update("commentMapper.updateComment", comment);
	}
}
