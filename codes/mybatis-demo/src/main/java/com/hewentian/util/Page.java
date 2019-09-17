package com.hewentian.util;

/**
 * 
 * <p>
 * <b>Page</b> 是 分页类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:51:38
 * @since JDK 1.7
 * 
 */
public class Page {

	// 总条数
	private long totalCount = 0;
	// 当前页码
	private int pageNum = 1;

	// 每页显示多少条数
	private int numPerPage = 20;

	// 显示多少页码
	private int pageNumShown = 8;

	// 排序字段
	private String orderField;

	// 排序方式
	private String orderDirection = "asc";

	// 查询条件
	private String keywords;

	// 从第几条数据开始查询
	public int getStartRow() {
		return (pageNum - 1) * numPerPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
