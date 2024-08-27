package edu.kh.project.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AroundAspect {
	
	@Order(2)
	// 굳이 처리해줘야하나? before, after 썼는데?
	// before가 여러개가 있을 때 효과가 있는 것
	// 같은 친구들 순서를 지정할 때 사용하는 것
	
	// 전처리, 후처리를 모두 해결하고자 할 때 사용하는 어드바이스
	// proceed() 메소드 호출 전 : @Before advice 작성
	// proceed() 메소드 호출 후 : @After advice 작성
	// 메소드 마지막에 proceed()의 반환값을 리턴해야함
	@Around("CommonPointcut.ServiceImplPointcut()")
	   public Object aroundServiceLogs(ProceedingJoinPoint pp) throws Throwable {
	      // @Around advice는 JoinPoint Interface가 아닌
	      //  하위 타입인 ProceedingJoinPoint를 사용해야 함.
	      
	      
	      long startMs = System.currentTimeMillis(); // 서비스 시작 시의 ms 값
	      // 시간을 얻어오는 것
	      
	      Object obj = pp.proceed(); // 여기가 기준
	      
	      long endMs = System.currentTimeMillis(); // 서비스 종료 시의 ms 값
	      // 끝난 후
	      
	      String str = "Running Time : " + (endMs- startMs) + "ms";   

	      log.info(str);
	      
	      return obj;
	      
	   }

}
