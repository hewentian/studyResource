package com.hewentian.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hewentian.entity.Student;
import com.hewentian.service.IStudentService;
import com.hewentian.util.JuiResult;
import com.hewentian.util.Page;

/**
 * 
 * <p>
 * <b>StudentController</b> 是 学生类Controller
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午6:13:12
 * @since JDK 1.7
 * 
 */
@Controller
@RequestMapping("studentController")
public class StudentController extends BaseController {

	@Resource(name = "studentService")
	IStudentService studentService;

	/**
	 * 获取列表
	 * 
	 * @date 2016年8月24日 下午3:29:20
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(Model model, String name, Page juiPage) {
		List<Student> list = null;

		try {
			list = studentService.getList(name, juiPage);
			if (null != list && !list.isEmpty()) {
				long count = studentService.getCount(name);
				juiPage.setTotalCount(count);
			}
		} catch (Exception e) {
			return ajaxDoneError("系统错误 ");
		}

		model.addAttribute("list", list);
		model.addAttribute("name", name);
		model.addAttribute("page", juiPage);
		return new ModelAndView("studentList");
	}

	/**
	 * 编辑
	 * 
	 * @date 2016年8月25日 上午10:57:33
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Model model, String id) {
		if (StringUtils.isNotBlank(id)) {
			try {
				Student student = studentService.getById(id);
				model.addAttribute("student", student);
			} catch (Exception e) {
			}
		}

		return new ModelAndView("studentEdit");
	}

	/**
	 * 保存
	 * 
	 * @date 2016年8月25日 上午11:23:46
	 * @param model
	 * @param student
	 * @param oldId
	 * @param juiResult
	 * @return
	 */
	@RequestMapping("save")
	public ModelAndView save(Model model, Student student, String oldId, JuiResult juiResult) {

		if (null == student || StringUtils.isBlank(student.getId())) {
			return ajaxDoneParamError();
		}

		String res = null;
		try {
			res = studentService.save(student, oldId);
		} catch (Exception e) {
			res = e.getMessage();
		}

		if (StringUtils.isBlank(res)) {
			return ajaxDoneSuccess("操作成功", juiResult.getNavTabId());
		} else {
			return ajaxDoneError(res);
		}
	}

	/**
	 * 删除
	 * 
	 * @date 2016年8月25日 下午3:16:04
	 * @param model
	 * @param id
	 * @param juiResult
	 * @return
	 */
	@RequestMapping("delete")
	public ModelAndView delete(Model model, String id, JuiResult juiResult) {

		if (StringUtils.isBlank(id)) {
			return ajaxDoneParamError();
		}

		String res = null;
		try {
			res = studentService.delete(id);
		} catch (Exception e) {
			res = e.getMessage();
		}

		if (StringUtils.isBlank(res)) {
			return ajaxDoneSuccess(false, juiResult.getNavTabId());
		} else {
			return ajaxDoneError(res);
		}
	}
}