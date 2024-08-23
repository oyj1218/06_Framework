package edu.kh.project.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.project.board.model.service.BoardService;

// 스프링이 일정 시간마다 해당 객체를 이용해서 코드를 수행
// == 스프링이 해당 클래스를 객체로 만들어서 관리 해야함
// == Bean 등록

@Component // @Controller, @Service, @Repoistory의 부모 어노테이션
		   // Bean으로 등록하겠다고 명시하는 어노테이션
public class ImageDeleteScheduling {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BoardService service;
	
	
	// 1. @Scheduled(fixedDelay = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
	// 일(5초) -> 10초 대기 -> 일(5초) -> 10초 대기
	
	// 2. @Scheduled(fixedRate = 10000)
	// 일(5초) & 대기(10초)
	// 대기시간이 줄어듦
	// 항상 같은 간격으로 기능을 실행시켜줘서 fixedRate를 좀 더 많이 사용한다
	
	// @Scheduled(cron = "0,30 * * * * *") // 매 분 0초, 30초 마다
	@Scheduled(cron = "0 0 * * * *") // 매 정시(*시 0분 0초)
	// [] : 생략할 수 있다
	// cron 순서대로 cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)  에 맞춰서 넣어주면 된다
	public void test() {
		// 돌려보낼 것이 없어서, 알아서 시간이 되면 끝
		// void : 돌려보낼 것이 없다
		
		// System.out.println("스케쥴러가 일정 시간마다 자동으로 출력");
		
		System.out.println("---------- 게시판 DB, 서버 불일치 파일 제거 ----------");
		// 서버에 저장된 파일 목록을 조회해서
		// DB에 저장된 파일 목록과 비교 후
		// 매칭되지 않는 서버 파일 제거
		
		// 1. 서버에 저장된 파일 목록 조회
		String filePath = servletContext.getRealPath("/resources/images/board");
		// c:\workspace\6_Framework\boardProject\src\main\webapp\resources\images\board
		
		// 2. filePath에 저장된 모든 파일 목록 읽어오기
		File path = new File(filePath);
		File[] imageArr = path.listFiles();
		// 배열인데, 처음에만 사용하고 별로 사용 안 함(map, list 를 더 많이 사용)
		// 기능이 별로 없어서
		// -> 배열 다루기 힘들다, lsitFiles로 만든다
		
		// 배열 -> List로 변환
		List<File> serverImageList = Arrays.asList(imageArr);
		
		// 임시 확인
		/*
		 * for(File f : serverImageList) {
			System.out.println(f.toString());
		}
		*/
		
		// 3. DB 이미지 파일 목록 조회
		List<String> imageList = service.imageList();
		
		// 임시 확인
		/* for(String imgList : imageList) {
			System.out.println(imgList.toString());
		} */
		
		// 4. 서버에 파일 목록이 있을 경우에 비교 시작
		if(!serverImageList.isEmpty()) { // list는 isEmpty() 사용
			
			// 5. 서버 파일 목록을 순차적으로 접근
			for(File server : serverImageList) {
				// 6. 서버에는 존재하는 파일이 DB(imageList)에 없다면 삭제하겠다
				
				// 원래는 c:\workspace\6_Framework\boardProject\src\main\webapp\resources\images\board\이미지명인데
				// db 조회에서는 이미지명만 나옴
				// server.toString().split("\\\\")
				// \\ 2개인 이유? 자바에선 \ 1개만 쓰면 문자열로 인식해서 \\로 해줘야함
				
				// 이렇게만 짜르면 못 쓰니깐, 추가로 넣어줘야 함
				
				// String[] temp = server.toString().split("\\\\");
				// 찾고자 하는 인덱스가 제일 마지막일 것
				// temp.length-1
				// String s = temp[temp.length-1];
				
				// -- 여기까지 split로 한건데 서버에서 해주는 방법도 있다
				// System.out.println(server.getName());
				
				
				// List.indexOf(객체) : 객체가 List에 있으면 해당 인덱스 반환
				// 					   없으면 -1 반환해준다
				
				if(imageList.indexOf(server.getName()) == -1) {
					// DB 파일 목록 		// 서버 파일 이름
					
					// 파일 삭제
					System.out.println(server.getName() + "삭제");
					// File.delete() : 파일 삭제
					server.delete();
				}
				
				
			} // for문 종료
			System.out.println("------ 이미지 파일 삭제 스케쥴러 종료 ------");
		}
						
	}
	
	/*
	 * @Scheduled
	 * 
	 * * Spring에서 제공하는 스케줄러 - 스케줄러 : 시간에 따른 특정 작업(Job)의 순서를 지정하는 방법.
	 * 
	 * 설정 방법 
	 * 1) servlet-context.xml -> Namespaces 탭 -> task 체크 후 저장
	 * 2) servlet-context.xml -> Source 탭 -> <task:annotation-driven/> 추가
	 * 
	 *
	 * @Scheduled 속성
	 *  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
	 *    @Scheduled(fixedDelay = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
	 *    
	 *  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
	 *    @Scheduled(fixedRate  = 10000) // 이전 작업이 시작된 후 10초 뒤에 실행
	 *    
	 *    
	 * * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT) 
	 * ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2 " // 연도 생략 가능
	 * 
	 * - 특수문자 * : 모든 수. 
	 * - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하 
	 * , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정 
	 * / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다 
	 * ? : 특별한 값이 없음. (월, 요일만 해당) 
	 * L : 마지막. (월, 요일만 해당)
	 * @Scheduled(cron="0 * * * * *") // 매 분마다 실행
	 * 
	 * 
	 * 
	 * 
	 * * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
	 * 
	 */

	

}
