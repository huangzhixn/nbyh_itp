package com.sunyard.itp.utils;

import java.io.Serializable;

/**
 * 
 * @author daox.nie
 * @created 2016年11月26日 下午4:44:11
 * @description
 */
public class IPage implements Serializable{

	/** 
	 * @Fields serialVersionUID : 序列号UID
	 */ 
	private static final long serialVersionUID = -619460273010093308L;
	
	/** 
	 * @Fields pageNo : 当前页
	 */ 
	private int pageNo;  
	/** 
	 * @Fields pageCount :总页数
	 */ 
	private int pageCount; 
	/** 
	 * @Fields resultCount : 总记录数
	 */ 
	private int resultCount;
	/** 
	 * @Fields pageSize : 每页显示条数
	 */ 
	private int pageSize;
	/** 
	 * @Fields pageFirstId : 当前页首序号
	 */ 
	private int pageFirstId;
	/** 
	 * @Fields pageLastId : 当前页尾序号
	 */ 
	private int pageLastId;
	
	private int maxPageSize = 50;
	private int minPageSize = 5;
	
	public IPage() {
	}
	
	public IPage(int pageNo, int pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
		pageFirstId = (getPageNo() - 1) * getPageSize();
		pageLastId = pageFirstId + getPageSize();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo<=0)
		{
			pageNo=1;
		}
		this.pageNo = pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > maxPageSize)
		{
			pageSize = maxPageSize;
		}
		if (pageSize < minPageSize)
		{
			pageSize = minPageSize;
		}
		this.pageSize = pageSize;
	}

	public int getPageFirstId() {
		return pageFirstId;
	}

	public void setPageFirstId(int pageFirstId) {
		this.pageFirstId = pageFirstId;
	}

	public int getPageLastId() {
		return pageLastId;
	}

	public void setPageLastId(int pageLastId) {
		this.pageLastId = pageLastId;
	}

	@Override
	public String toString() {
		return "IPage [pageNo=" + pageNo + ", pageCount=" + pageCount
				+ ", resultCount=" + resultCount + ", pageSize=" + pageSize
				+ ", pageFirstId=" + pageFirstId + ", pageLastId=" + pageLastId
				+ "]";
	}
}
