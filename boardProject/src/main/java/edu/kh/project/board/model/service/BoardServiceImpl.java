package edu.kh.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dao.BoardDAO;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.Pagination;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO dao;

	// 게시판 종류 목록 조회
	@Override
	public List<Map<String, Object>> selectBoardTypeList() {
		return dao.selectBoardTypeList();
	}

	// 게시글 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {

		// 1. 특정 게시판의 삭제되지 않은 게시글 수 조회
		int listCount = dao.getListCount(boardCode);

		// 2. 1번 조회 결과 + cp를 이용해서 Pagination 객체 생성
		Pagination pagination = new Pagination(cp, listCount);

		System.out.println(pagination);

		// 3. 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회
		// 특정 게시판 : boardCode
		// 현재 페이지 : pagination.currentPage
		// 게시글 몇 개 : pagination.limit
		// 현재 페이지 + 게시글 몇 개 = 모두 다 pagination 안에 있으니깐 한번에 넘겨주기
		List<Board> boardList = dao.selectBoardList(pagination, boardCode);
		// 여러개이니깐 List, 타입제한 Board

		// 4. pagination, boardList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);

		return map;
	}

	// 게시글 상세 조회
	@Override
	public Board selectBoard(Map<String, Object> map) {
		return dao.selectBoard(map);
	}

	// 좋아요 조회
	@Override
	public int boardLikeCheck(Map<String, Object> map) {
		return dao.boardLikeCheck(map);
	}

	// 좋아요 처리
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int like(Map<String, Integer> paramMap) {
		int result = 0;
		
		if (paramMap.get("check") == 0) {
			// check가 안 되어 있다면
			result = dao.likeInsert(paramMap);

		} else {
			// check가 되어있다면
			result = dao.likeDelete(paramMap);
		}

		// SQL 수행 결과가 0이라면 DB 또는 파라미터에 문제가 있다
		// -> 에러를 나타내는 임의의 값을 반환(-1)
		// 음수로 잘 안 쓰는 숫자를 사용한다
		if(result == 0) return -1;
		
		// 현재 게시글의 좋아요 개수 조회
		// 실시간이니깐 개수 먼저 조회하면 안되고, 삽입 삭제 후에 해줘야 한다
		int count = dao.likeCount(paramMap.get("boardNo"));
		return count;

	}

	/**
	 * 조회수 증가
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateReadCount(int boardNo) {
		return dao.updateReadCount(boardNo);
	}

	/**
	 * 게시글 목록 조회(검색)
	 */
	@Override
	public Map<String, Object> selectBoardList(Map<String, Object> paramMap, int cp) {
		// 1. 특정 게시판의 삭제되지 않고 검색 조건이 일치하는 게시글 수 조회
		int listCount = dao.getListCount(paramMap);

		// 2. 1번 조회 결과 + cp를 이용해서 Pagination 객체 생성
		Pagination pagination = new Pagination(cp, listCount);
		
		// 3. 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회
		// 단, 검색 조건이 일치하는 글만 조회해야 한다
		List<Board> boardList = dao.selectBoardList(paramMap, pagination);
		
		// 4. pagination, boardList를 Map에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		

		return map;
	}

}
