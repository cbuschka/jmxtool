package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.Set;

public class ListMBeansCommand implements Command
{
	@Autowired
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "List MBeans.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");

		MBeanServerConnection jmxConn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		String query = commandLine.getOpt("query", "*:*");
		ObjectName jmxQuery = new ObjectName(query);
		Set<ObjectName> objectNames = jmxConn.queryNames(jmxQuery, null);
		for (ObjectName objectName : objectNames)
		{
			System.out.println(objectName);
		}
	}
}
