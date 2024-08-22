package edu.kh.project.board.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImage;

@Repository
public class BoardDAO2 {
	

	@Autowired // bean 중에서 타입이 같은 객체를 DI
	private SqlSessionTemplate sqlSession;

	public int boardInsert(Board board) {
		int result = sqlSession.insert("boardMapper.boardInsert", board);
		
		// -> sql 수행 후 매개변수 board 객체에는 boardNo(원래는 없었는데 수행 후에 생겼다) 존재 O
		
		// 삽입 성공 시
		if(result > 0) result = board.getBoardNo();
		
		return result; // 삽입 성공 시 boardNo, 실패 시 0 반환
		
	}
	
	/**
	 * 이미지 리스트(여러 개) 삽입
	 * @param uploadList
	 * @return
	 */
	public int insertImageList(List<BoardImage> uploadList) {
		return sqlSession.insert("boardMapper.insertImageList", uploadList);
	}
	
	/** 게시글 수정
	 * @param board
	 * @return rowCount
	 */
	public int boardUpdate(Board board) {
		return sqlSession.update("boardMapper.boardUpdate", board);
	}

	/** 이미지 삭제
	 * @param deleteMap
	 * @return rowCount
	 */
	public int imageDelete(Map<String, Object> deleteMap) {
		return sqlSession.delete("boardMapper.imageDelete", deleteMap);
	}

	/** 이미지 수정
	 * @param img
	 * @return rowCount
	 */
	public int imageUpdate(BoardImage img) {
		return sqlSession.update("boardMapper.imageUpdate", img);
	}

	/** 이미지 삽입
	 * @param img
	 * @return rowCount
	 */
	public int imageInsert(BoardImage img) {
		return sqlSession.insert("boardMapper.imageInsert", img);
	}

	/**
	 * 게시글 삭제
	 * @param map
	 * @return rowCount
	 */
	public int boardDelete(Map<String, Object> map) {
		return sqlSession.update("boardMapper.boardDelete", map);
	}

}
