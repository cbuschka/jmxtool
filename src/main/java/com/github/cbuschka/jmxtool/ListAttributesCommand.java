package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class ListAttributesCommand implements Command
{
	@Autowired
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "List attributes.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String objectName = commandLine.getRequiredOpt("objectName");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");

		MBeanServerConnection jmxConn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		ObjectName jmxObjectName = new ObjectName(objectName);
		MBeanInfo mBeanInfo = jmxConn.getMBeanInfo(jmxObjectName);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		for (MBeanAttributeInfo jmxAttributeInfo : attributes)
		{
			System.out.println(jmxAttributeInfo.getName());
		}
	}
}
