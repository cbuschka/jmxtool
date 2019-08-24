package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class GetAttributeCommand implements Command
{
	@Autowired
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "Get MBean attribute.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String objectName = commandLine.getRequiredOpt("objectName");
		String attributeName = commandLine.getRequiredOpt("attributeName");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");

		MBeanServerConnection jmxConn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		ObjectName jmxObjectName = new ObjectName(objectName);
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		System.out.print(String.format("%s:%s=%s", objectName, attributeName, value));
	}
}
