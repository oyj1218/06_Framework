package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;

// @Controller + @ResponseBody
@RestController // 요청/응답 처리(단, 모든 요청/응답은 비동기)
			// -> REST API 구축하기 위한 Controller

public class CommentController {
	
	@Autowired
	private CommentService service;
	
	// 댓글 목록 조회
	@GetMapping(value="/comment", produces="application/json; charset=UTF-8")
	// @ResponseBody -> 근데 이거 모두 다 일일히 써줘야한다 -> 불편하다 -> controller에 의미를 합쳐서 넣어준다
	public List<Comment> selectComment(int boardNo) {
		return service.selectComment(boardNo); // List -> JSON 변환(HttpMessageConverter)
										// 변환 과정에서 한글이 깨짐
		
	}
	
	// @RequestBody : http요청의 본문이 그대로 전달
	// HTTP 요청의 바디내용을 통째로 자바객채로 변환해서 매핑된 메소드 파라미터로 전달함
	
	// 댓글 생성
	@PostMapping("/comment")
	public int insertComment(@RequestBody Comment comment) {
		// 요청 데이터(JSON)를 HttpMessageConverter가 해석해서 Java 객체(Comment)에 대입
		// 파라미터에 dto를 쓰면 저기에 알아서 넣어준다
		return service.insertComment(comment);
	}
	
		
	// 댓글 삭제
	@DeleteMapping("/comment")
	public int deleteComment(@RequestBody int commentNo) {
		// ajax 요청 시 body에 담겨있는 하나 뿐인 데이터는 매개변수 commentNo에 담기게 된다
		return service.deleteComment(commentNo);
	}

	
	
	// 댓글 수정
	@PutMapping("/comment")
	public int updateComment(@RequestBody Comment comment) {
		return service.updateComment(comment);
		
	
	}
	
	
	

}
