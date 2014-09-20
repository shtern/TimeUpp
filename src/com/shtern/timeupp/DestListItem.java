package com.shtern.timeupp;

public class DestListItem {
	String mDest;
	String mName;
	String mTime;
	public DestListItem(String dest, String name,String time)
	{
		mDest=dest;
		mName=name;
		mTime=time;
	}
	public void setDest(String dest)
	{
		mDest=dest;
	}
	public String getDest()
	{
		return mDest;
	}
	public void setName(String name)
	{
		mName=name;
	}
	public String getName()
	{
		return mName;
	}
	public void setTime(String time)
	{
		mTime=time;
	}
	public String getTime()
	{
		return mTime;
	}
}
