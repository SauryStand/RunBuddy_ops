package com.runbuddy.controll;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class LoginController {
	
	@Autowired
	private LoginServiceI loginServiceI;

	public LoginServiceI getLoginServiceI() {
		return loginServiceI;
	}

	public void setLoginServiceI(LoginServiceI loginServiceI) {
		this.loginServiceI = loginServiceI;
	}
	
	//@RequestMapping(value = "/login")
	public String listStus(HttpServletRequest request,HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("进入loginServlet了!");
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String imageValue = request.getParameter("imageValue");
		System.out.println("imageValue:" + imageValue);
		String remember = request.getParameter("remember");
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		request.setAttribute("imageValue", imageValue);
		String sRand = (String) session.getAttribute("sRand");
		System.out.println("sRand=" + sRand);
		
		//String mainPage="head.jsp";
		//RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
		//dispatcher.forward(request, resp);
		
		String mainPage = "displaystudent.jsp";
		request.setAttribute("mainPage", mainPage);
		return "main";
	}
	
	
}
