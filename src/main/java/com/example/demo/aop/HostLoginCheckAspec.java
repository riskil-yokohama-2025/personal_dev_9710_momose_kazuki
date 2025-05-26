package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.HostModel;
import com.example.demo.repository.HostRepository;

@Aspect
@Component
public class HostLoginCheckAspec {
	
	@Autowired
	HostModel hostModel;
	
	@Autowired
	HostRepository hostRepository;
	
	@Around("execution(* com.example.demo.controller.admin.AdminController.*(..)) ||"
			+ "execution(* com.example.demo.controller.admin.AdminAccountController.*(..)) ||"
			+ "execution(* com.example.demo.controller.admin.AdminCategoryController.*(..))")
	public Object checkAdminLogin(ProceedingJoinPoint jp) throws Throwable {
		if(hostModel == null || hostModel.getId() == null
			|| hostModel.getName() == null || hostModel.getName().length() == 0
			|| hostRepository.findById(hostModel.getId()).get().getDeleteFlag()) {
			return "redirect:/admin/login?error=notLoggedIn";
		}
		return jp.proceed();
	}
}
