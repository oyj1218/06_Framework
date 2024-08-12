package edu.kh.project.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.service.BoardService;

@SessionAttributes({ "loginMember" })
@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardService service;

	/**
	 * 목록 조회 : /board/1?cp=1 (cp : current page(현재 페이지)) 상세 조회 : /board/1/1500?cp=1
	 * 
	 * ** 컴트롤러 따로 사용 예정 삽입 : /board2/insert?code=1 (code == BOARD_CODE, 게시판 종류) 수정 :
	 * /board2/update?code=1&no=1500 (no == BOARD_NO, 게시글 번호) 삭제 :
	 * /board2/delete?code=1&no=1500
	 * 
	 */
	// @PathVariable
	// URL 경로에 있는 값을 매개변수로 이용할 수 있게 하는 어노테이션

	// @PathVariable을 사용하는 경우
	// 오래되지 않았다
	// - 자원(resource) 구분/식별
	// ex) github.com/userId : 사람들한테 보여줘야한다, 식별해야 할 때
	// ex) /board/1 -> 1번(공지사항) 게시판
	// ex) /board/2 -> 2번(자유) 게시판

	// Query String을 사용하는 경우
	// - 정렬, 필터링
	// ex) search.naver.com?query=날씨
	// ex) search.naver.com?query=점심메뉴
	// ex) /board2/insert?code=1
	// ex) /board2/insert?code=2
	// -> 삽입이라는 공통된 동작 수행 단, code에 따라 어디에 삽입할지 지정(필터링)

	// ex) /board/list?order=recent (최신순)
	// ex) /board/list?order=popular (인기순)

	// 게시글 목록 조회
	@GetMapping("/{boardCode}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, Model model) {
		// cp 사용해야 하는데 어떻게 얻어오기? @RequestParam
		// <a href="/board/${boardType.BOARD_CODE}">${boardType.BOARD_NAME}</a>
		// 근데 없을수도 있으니깐 required=false, 그럼 false이면 defaultValue="1"으로 해준다

		// boardCode 확인
		System.out.println("boardCode : " + boardCode);
		Map<String, Object> map = service.selectBoardList(boardCode, cp);

		// 조회 결과를 request scope에 세팅해서 보내주면 된다
		model.addAttribute("map", map);

		// 모델은 k:v로 가져오는 것,
		// model.addAttribute(map) 이건 k,v 둘 다 문자열이 아니기에 toString화 되어서 엄청 길게 작성하게 된다

		return "board/boardList";
	}

	// @PathVariable : 주소에 지정된 부분을 변수에 저장
	// + request scope 세팅

	// 게시글 상세 조회
	@GetMapping("/{boardCode}/{boardNo}")
	public String boardDetail(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
			Model model // 데이터 전달용 객체
			, RedirectAttributes ra // 리다이렉트 시 데이터 전달용 객체
	) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);

		// 게시글 상세 조회 서비스 호출
		Board board = service.selectBoard(map);

		String path = null;

		if (board != null) { // 조회 결과가 있을 경우

			path = "board/boardDetail"; // forward 할 jsp 경로
			model.addAttribute("board", board);

		} else { // 조회 결과가 없을 경우
			path = "redirect:/board/" + boardCode; // 게시판 첫 페이지로 리다이렉트

			ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다.");
		}

		return path;
	}
}
