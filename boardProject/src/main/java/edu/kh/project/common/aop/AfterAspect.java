package edu.kh.project.common.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AfterAspect {
	
	@Order(3)
	@After("CommonPointcut.ServiceImplPointcut()") // 타겟 수행 전
	public void afterlog() {
		log.info("-----------------------\n");
	}
}
