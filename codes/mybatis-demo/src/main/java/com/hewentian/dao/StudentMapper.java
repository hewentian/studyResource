package com.hewentian.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hewentian.entity.Criteria;
import com.hewentian.entity.Student;
import com.hewentian.entity.StudentCriteria;

/**
 * 
 * <p>
 * <b>StudentMapper</b> 是 学生类的mapper
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:56:49
 * @since JDK 1.7
 * 
 */
public interface StudentMapper {
	/**
	 * 获取列表
	 * 
	 * @date 2016年8月24日 下午2:55:51
	 * @return
	 */
	public List<Student> getList(StudentCriteria criteria) throws Exception;

	/**
	 * 统计列表大小
	 * 
	 * @date 2016年8月24日 下午6:37:55
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public long getCount(StudentCriteria criteria) throws Exception;

	public long getCountNew(Criteria criteria);

	/**
	 * 根据id获取记录
	 * 
	 * @date 2016年8月25日 上午10:55:16
	 * @param id
	 * @return
	 */
	public Student getById(String id) throws Exception;

	/**
	 * 增加新记录
	 * 
	 * @date 2016年8月25日 上午11:51:54
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int add(Student student) throws Exception;

	/**
	 * 更新
	 * 
	 * @date 2016年8月25日 下午2:46:21
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int update(Student student) throws Exception;

	/**
	 * 修改userid的更新
	 * 
	 * @date 2016年8月25日 下午3:19:32
	 * @param student
	 * @param oldId
	 * @return
	 */
	public int updateByUserid(@Param("student") Student student, @Param("oldId") String oldId);

	/**
	 * 根据id删除
	 * 
	 * @date 2016年8月25日 下午3:20:49
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(String id) throws Exception;

}