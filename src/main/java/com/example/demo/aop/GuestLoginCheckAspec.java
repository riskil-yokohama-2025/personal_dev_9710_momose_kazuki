package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.GuestModel;
import com.example.demo.model.HostModel;
import com.example.demo.repository.GuestRepository;

@Aspect
@Component
public class GuestLoginCheckAspec {
	
	@Autowired
	GuestModel guestModel;
	
	@Autowired
	HostModel hostModel;
	
	@Autowired
	GuestRepository guestRepository;
	
	@Around("execution(* com.example.demo.controller.ThraedController.*(..)) ||"
			+ "execution(* com.example.demo.controller.CommentController.*(..)) ||"
			+ "execution(* com.example.demo.controller.UserSettingController.*(..))")
	public Object checkUserLogin(ProceedingJoinPoint jp) throws Throwable {
		if(guestModel == null || guestModel.getId() == null
			|| guestModel.getName() == null || guestModel.getName().length() == 0) {
			return "redirect:/login?error=notLoggedIn";
		} else if(guestRepository.findById(guestModel.getId()).get().getBanFlag()) {
			return "redirect:/login?error=userBan";
		} else if(guestRepository.findById(guestModel.getId()).get().getDeleteFlag()) {
			return "redirect:/login?error=userDelete";
		}
		return jp.proceed();
	}
	
}


