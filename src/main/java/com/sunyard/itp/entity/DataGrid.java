package com.sunyard.itp.entity;
import java.io.Serializable;
import java.util.List;

import com.sunyard.itp.constant.CommonConst;
/**
 * 
 * Description:
 * @author: daox.nie 
 * date:   2017年1月3日 下午11:02:28   
 * @param <T>
 */
public class DataGrid<T> extends LogStatus implements Serializable
{

	/**   
	 * 
	 */
	private static final long serialVersionUID = 2675008828979304664L;

//	/**
//	 * @Fields backResult : backResult
//	 */
//	private BackResult backResult = new BackResult(CommonConst.RET_SUCCESS, "默认成功");

	/**
	 * @Fields total : 查询结果总记录数
	 */
	private long total;

	/**
	 * @Fields rows : 返回页面的查询记录结果列表
	 */
	private List<T> rows;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 无参构造函数
	 * </p>
	 */
	public DataGrid()
	{
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 有参构造函数
	 * </p>
	 * 
	 * @param total
	 * @param rows
	 */
	public DataGrid(long total, List<T> rows)
	{
//		this.backResult = new BackResult();
		this.total = total;
		this.rows = rows;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setDataGrid(long total,List rows)
	{
		this.total = total;
		this.rows =rows;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 有参构造函数
	 * </p>
	 * 
	 * @param backResult
	 * @param total
	 * @param rows
	 */
	public DataGrid(BackResult backResult, long total, List<T> rows)
	{
//		this.backResult = backResult;
		this.total = total;
		this.rows = rows;
	}

	/**
	 * @Return the backResult
	 */
//	public BackResult getBackResult()
//	{
//		return backResult;
//	}
//
//	/**
//	 * @Param backResult the backResult to set
//	 */
//	public void setBackResult(BackResult backResult)
//	{
//		this.backResult = backResult;
//	}

	/**
	 * @Return the total
	 */
	public long getTotal()
	{
		return total;
	}

	/**
	 * @Param total the total to set
	 */
	public void setTotal(long total)
	{
		this.total = total;
	}

	/**
	 * @Return the rows
	 */
	public List<T> getRows()
	{
		return rows;
	}

	/**
	 * @Param rows the rows to set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setRows(List rows)
	{
		this.rows = rows;
	}

	@Override
	public String toString()
	{
		return "DataGrid [total=" + total + ", rows=" + rows + "]";
	}

}
