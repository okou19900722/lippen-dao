package org.okou.lippen.dao.annotation.mybatis;

public class LippenParam<T>
{
	private T po;
	private Integer firstResult;
	private Integer maxResults;
	
	public LippenParam(T po)
	{
		this(po, null, null);
	}
	public LippenParam(T po, Integer maxResults)
	{
		//limit num 表示从0开始取num个，所以此处firstResult为0
		this(po, 0, maxResults);
	}
	public LippenParam(T po, Integer firstResult, Integer maxResults)
	{
		super();
		this.po = po;
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}
	public T getPo()
	{
		return po;
	}
	public Integer getFirstResult()
	{
		return firstResult;
	}
	public Integer getMaxResults()
	{
		return maxResults;
	}
	
	
	public void setId(Object o)
	{
		System.err.println("set id" + o);
	}
}
