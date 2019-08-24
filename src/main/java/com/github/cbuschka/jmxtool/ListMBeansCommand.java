package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;
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
		Map<String, Object> env = new HashMap<>();
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");
		if (user != null && password != null)
		{
			env.put(JMXConnector.CREDENTIALS, new String[]{user, password});
		}

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		String query = commandLine.getOpt("query", "*:*");
		ObjectName jmxQuery = new ObjectName(query);
		Set<ObjectName> objectNames = jmxConn.queryNames(jmxQuery, null);
		for (ObjectName objectName : objectNames)
		{
			System.out.println(objectName);
		}
	}
}
