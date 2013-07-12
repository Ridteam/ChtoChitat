package com.ridteam.livejournal.chtochitat.xmlrpc.entities;

import java.util.HashMap;
import java.util.Map;

public class BaseEntity
{
	private Map<String, Object> mEntityMap = new HashMap<String, Object>();

	public BaseEntity()
	{}

	public void put(final String name, final Object value)
	{
		mEntityMap.put(name, value);
	}

	public Map<String, Object> getMap()
	{
		return mEntityMap;
	}
}
