package com.github.cbuschka.jmxtool;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class AttributeLocator
{
	private ObjectName objectName;
	private String attributeName;

	public AttributeLocator(String objectName, String attributeName) throws MalformedObjectNameException
	{
		this.objectName = new ObjectName(objectName);
		this.attributeName = attributeName;
	}

	public String getAttributeName()
	{
		return attributeName;
	}

	public ObjectName getObjectName()
	{
		return objectName;
	}
}
