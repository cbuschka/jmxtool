package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Set;

public class ListMBeansCommand implements Command
{
	@Override
	public String getDescription()
	{
		return "List MBeans.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		ObjectName query = new ObjectName("*:*");
		Set<ObjectName> objectNames = jmxConn.queryNames(query, null);
		for (ObjectName objectName : objectNames)
		{
			System.out.println(objectName);
		}
	}
}
