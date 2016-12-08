package io.nzo.booth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import io.nzo.booth.model.User;
import io.nzo.booth.service.UserService;

@Controller
@SessionAttributes("user")
public class UserController
{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	// 가입
	// /sign/up
	@RequestMapping(path =  "/sign/up", method = RequestMethod.GET, produces = MediaType.TEXT_HTML )
	public String signUp(Model model, HttpServletRequest request,
			@RequestParam( name="ref", required = false , defaultValue = "") String referer)
	{
		User user = new User();
		model.addAttribute("userForm", user);
		
		// referer
		model.addAttribute("ref", "http://google.com/");
		
		return "signUp";
	}
	
	@RequestMapping(path = "/sign/up", method = RequestMethod.POST)
	public ModelAndView register(Model model,
			@RequestParam( name="ref", required = false , defaultValue = "") String referer,
			@ModelAttribute("userForm") User user)
	{
		// 중복체크
		User userDuplicate = userService.getUserForUsername(user.getUsername());
		if( userDuplicate == null )
		{
			userService.create(user);
			
			user.setPasswordSha2("");
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/sign/up");
			return mv;
		}
		else
		{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/sign/up");
			return mv;
		}
	}
	
	// 로그인
	// /sign/in
	@RequestMapping(path =  "/sign/in", method = RequestMethod.GET, produces = MediaType.TEXT_HTML )
	public String signIn(Model model, HttpServletRequest request,
			@RequestParam( name="ref", required = false , defaultValue = "") String referer)
	{
		User user = new User();
		model.addAttribute("userForm", user);
		
		// referer
		model.addAttribute("ref", "http://google.com/");
		
		return "signIn";
	}
	
	@RequestMapping(path = "/sign/in", method = RequestMethod.POST)
	public ModelAndView login(Model model, HttpSession session,
			@RequestParam( name="ref", required = false , defaultValue = "") String referer,
			@ModelAttribute("userForm") User user)
	{
		if( session.getAttribute("user") != null )		// 세션 확인
		{
			// nothing
			User sessionUser = (User)session.getAttribute("user");
			model.addAttribute(sessionUser);

			System.out.println("이미 로그인됨" + sessionUser.getName());
			
			
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/board/test");
			return mv;
		}
		else
		{
			User loginUser = userService.getUserWithLogin(user.getUsername(), user.getPasswordSha2());
			if( loginUser != null ) 
			{
				model.addAttribute(loginUser);
				session.setAttribute("user", loginUser);
				
				System.out.println("로그인 성공" + loginUser.getName());
				// 로그인성공
				ModelAndView mv = new ModelAndView();
				mv.setViewName("redirect:/board/test");
				return mv;
			}
			else
			{
				session.invalidate();
				
				System.out.println("로그인 실패");
				
				// 로그인실패
				ModelAndView mv = new ModelAndView();
				mv.setViewName("redirect:/sign/in");
				return mv;
			}
		}
	}
	
	// 로그아웃
	@RequestMapping(path = "/sign/out", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView logout(Model model, HttpSession session,
			@RequestParam( name="ref", required = false , defaultValue = "") String referer)
	{
		session.invalidate();
		
		if(model.containsAttribute("user"))
		{
			model.asMap().remove("user");
			System.out.println("모델 User 삭제됨");
		}
		
		System.out.println("로그아웃");
		
		// 로그인성공
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/board/test");
		return mv;
	}
}
