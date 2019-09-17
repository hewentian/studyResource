package com.hewentian.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hewentian.entity.SysUser;
import com.hewentian.util.IConst;

@Controller
// @RequestMapping("login")
public class LoginController extends BaseController {
	Logger logger  = Logger.getLogger(LoginController.class);


	@RequestMapping("login/logout")
	public ModelAndView logout(SysUser pageUser, HttpSession session) {
		session.removeAttribute(IConst.LOGIN_USER);
		return login();
	}

	@RequestMapping("login/index")
	public ModelAndView index(Model model, SysUser pageUser,
			String inputValidateCode, HttpSession session) {
		String message = null;
		try {
			Integer login = 1;/*userService.login(pageUser, inputValidateCode,
					session);*/
			// 1成功，2用户账号不存在，3密码错误，4账号已经停用
			if (login == 1) {
				session.setAttribute(IConst.LOGIN_USER, pageUser);
				model.addAttribute(IConst.LOGIN_USER, pageUser);
				model.addAttribute("message", message);
				return index();
			} else if (login == 2) {
				message = "用户账号不存在";
			} else if (login == 3) {
				message = "密码错误";
			} else if (login == 4) {
				message = "账号已经停用";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxDoneError("系统错误");
		}
		return new ModelAndView("login");
	}

	@RequestMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
}