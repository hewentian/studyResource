package com.hewentian.service;

import java.util.List;

import com.hewentian.entity.Student;
import com.hewentian.util.Page;

/**
 * 
 * <p>
 * <b>IStudentService</b> 是 IStudentService
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:50:23
 * @since JDK 1.7
 * 
 */
public interface IStudentService {
	/**
	 * 获取列表
	 * 
	 * @date 2016年8月24日 下午2:55:51
	 * @return
	 */
	public List<Student> getList(String name, Page page) throws Exception;

	/**
	 * 统计列表大小
	 * 
	 * @date 2016年8月25日 上午10:02:57
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public long getCount(String name) throws Exception;

	/**
	 * 根据id获取记录
	 * 
	 * @date 2016年8月25日 上午10:55:16
	 * @param id
	 * @return
	 */
	public Student getById(String id) throws Exception;

	/**
	 * 保存记录
	 * 
	 * @date 2016年8月25日 上午11:38:02
	 * @param student
	 * @param oldId
	 * @return 成功返回null or empty，否则返回错误原因
	 */
	public String save(Student student, String oldId) throws Exception;

	/**
	 * 根据id删除记录
	 * 
	 * @date 2016年8月25日 下午3:14:14
	 * @param id
	 * @return 成功返回null or empty，否则返回错误原因
	 * @throws Exception
	 */
	public String delete(String id) throws Exception;
}