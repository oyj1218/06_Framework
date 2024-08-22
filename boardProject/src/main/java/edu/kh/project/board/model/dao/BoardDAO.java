package edu.kh.project.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.Pagination;

@Repository
public class BoardDAO {

	@Autowired // bean 중에서 타입이 같은 객체를 DI
	private SqlSessionTemplate sqlSession;

	/**
	 * 게시판 종류 목록 조회
	 * 
	 * @return boardTypeList
	 */
	public List<Map<String, Object>> selectBoardTypeList() {
		return sqlSession.selectList("boardMapper.selectBoardTypeList");
	}

	/**
	 * 특정 게시판의 삭제되지 않은 게시글 수 조회
	 * 
	 * @param boardCode
	 * @return
	 */
	public int getListCount(int boardCode) {
		return sqlSession.selectOne("boardMapper.getListCount", boardCode);
	}

	/**
	 * 특정 게시판에서 현재 페이지에 해당하는 부분에 대한 게시글 목록 조회
	 * 
	 * @param boardCode
	 * @param pagination
	 * @return boardList
	 */
	public List<Board> selectBoardList(Pagination pagination, int boardCode) {
		// RowBounds 객체
		// - 마이바티스에서 페이징처리를 위해서 제공하는 객체
		// - offset 만큼 건너 뛰고 그 다음 지정된 행 개수(limit) 만큼 조회

		// 1) offset 계산
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();

		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		// 얼마만큼 건너뛸지, 얼마만큼 막을지 나온다

		// 3) selectList("namespace.id", 파라미터, RowBounds)
		// 이때 pagination 파라미터는? 굳이 넘겨줄 필요 없기에 안 쓴다
		return sqlSession.selectList("boardMapper.selectBoardList", boardCode, rowBounds);

	}

	/**
	 * 게시글 상세 조회
	 * 
	 * @param map
	 * @return board
	 */
	public Board selectBoard(Map<String, Object> map) {
		return sqlSession.selectOne("boardMapper.selectBoard", map);
	}

	/**
	 * 좋아요 조회
	 * 
	 * @param map
	 * @return
	 */
	public int boardLikeCheck(Map<String, Object> map) {
		return sqlSession.selectOne("boardMapper.boardLikeCheck", map);
	}

	/**
	 * 좋아요 삭제
	 * 
	 * @param paramMap
	 * @return result
	 */
	public int likeDelete(Map<String, Integer> paramMap) {
		return sqlSession.delete("boardMapper.likeDelete", paramMap);
	}

	/**
	 * 좋아요 넣기
	 * 
	 * @param paramMap
	 * @return result
	 */
	public int likeInsert(Map<String, Integer> paramMap) {
		return sqlSession.insert("boardMapper.likeInsert", paramMap);
	}

	/**
	 * 좋아요 개수 세기
	 * 
	 * @param integer
	 * @return result
	 */
	public int likeCount(Integer boardNo) {
		return sqlSession.selectOne("boardMapper.likeCount", boardNo);
	}

	/**
	 * 조회수 증가
	 * 
	 * @param boardNo
	 * @return
	 */
	public int updateReadCount(int boardNo) {
		return sqlSession.update("boardMapper.updateReadCount", boardNo);
	}

	/**
	 * 게시글 수 조회(검색)
	 * @param paramMap
	 * @return
	 */
	public int getListCount(Map<String, Object> paramMap) {
		return sqlSession.selectOne("boardMapper.getListCountForSearch", paramMap);
	}

	/**
	 * 특정 게시판 목록 조회(검색 조건이 일치하는 것만)
	 * @param pagination
	 * @param paramMap
	 * @return
	 */
	public List<Board> selectBoardList(Map<String, Object> paramMap, Pagination pagination) {
		// 1) offset 계산
		int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();

		// 2) RowBounds 객체 생성
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
		// 얼마만큼 건너뛸지, 얼마만큼 막을지 나온다
		
		// 3) selectList("namespace.id", 파라미터, RowBounds)
		// 이때 pagination 파라미터는? 굳이 넘겨줄 필요 없기에 안 쓴다
		// return sqlSession.selectList("boardMapper.selectBoardList", boardCode, rowBounds);
		return sqlSession.selectList("boardMapper.selectBoardListForSearch", paramMap, rowBounds);
	}

	/**
	 * 
	 * @param query
	 * @return
	 */

	public List<Board> autoComplete(String query) {
		RowBounds rowBounds = new RowBounds(0, 5);
		return sqlSession.selectList("boardMapper.searchAutoComplete", query, rowBounds);
	}
		




}
