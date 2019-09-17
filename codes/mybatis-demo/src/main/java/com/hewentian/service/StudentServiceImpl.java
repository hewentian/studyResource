package com.hewentian.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hewentian.dao.StudentMapper;
import com.hewentian.entity.Criteria;
import com.hewentian.entity.Student;
import com.hewentian.entity.StudentCriteria;
import com.hewentian.util.Page;

/**
 * 
 * <p>
 * <b>StudentServiceImpl</b> 是 StudentServiceImpl
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:52:56
 * @since JDK 1.7
 * 
 */
public class StudentServiceImpl implements IStudentService {
	private static Logger logger = Logger.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentMapper studentMapper;

	@Override
	public List<Student> getList(String name, Page juiPage) throws Exception {
		StudentCriteria criteria = new StudentCriteria();
		if (null == name) {
			name = "";
		}

		criteria.setName("name like '%" + name + "%'");

		if (juiPage != null) {
			RowBounds r = new RowBounds(juiPage.getStartRow(), juiPage.getNumPerPage());
			criteria.setRowBounds(r);
		}

		List<Student> list = studentMapper.getList(criteria);

		return list;
	}

	@Override
	public long getCount(String name) throws Exception {
		StudentCriteria criteria = new StudentCriteria();
		if (null == name) {
			name = "";
		}

		criteria.setName("name like '%" + name + "%'");

		long count = studentMapper.getCount(criteria);
		// 以下是另一种方式
		Criteria criteria2 = new Criteria();
		criteria2.put("name", name);
		long count2 = studentMapper.getCountNew(criteria2);
		System.out.println(count2);

		return count;
	}

	@Override
	public Student getById(String id) throws Exception {
		Student student = studentMapper.getById(id);
		return student;
	}

	@Override
	public String save(Student student, String oldId) throws Exception {
		String res = null;

		try {
			if (StringUtils.isBlank(oldId)) { // new record
				if (null != studentMapper.getById(student.getId())) {
					throw new Exception("该用户编号已经存在, 请输入其他编号.");
				} else {
					studentMapper.add(student);
				}
			} else { // update record
				if (student.getId().equals(oldId)) {
					// 可以直接更新
					studentMapper.update(student);
				} else {
					// 要查询是否已经存在新修改的userid
					if (null != studentMapper.getById(student.getId())) {
						throw new Exception("该用户编号已经存在, 请输入其他编号.");
					} else {
						studentMapper.updateByUserid(student, oldId);
					}
				}
			}
		} catch (Exception e) {
			res = e.getMessage();
			logger.error("", e);
		}

		return res;
	}

	@Override
	public String delete(String id) throws Exception {
		String res = null;

		try {
			studentMapper.delete(id);
		} catch (Exception e) {
			logger.error("", e);
			res = e.getMessage();
		}

		return res;
	}
}