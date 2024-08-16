package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Board;

public interface BoardService {

	/**
	 * 게시판 종류 목록 조회
	 * 
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/**
	 * 게시글 목록 조회
	 * 
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	/**
	 * 게시글 상세 조회
	 * 
	 * @param map
	 * @return board
	 */
	Board selectBoard(Map<String, Object> map);

	/**
	 * 좋아요 조회
	 * @param map
	 * @return
	 */
	int boardLikeCheck(Map<String, Object> map);

	/**
	 * 좋아요 삭제, 넣고, 개수 세기
	 * @param paramMap
	 * @return
	 */
	int like(Map<String, Integer> paramMap);

	/**
	 * 조회수 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	

	
}
