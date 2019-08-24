package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

public class GetAttributeCommand implements Command
{
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

		Map<String, Object> env = new HashMap<>();
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");
		if (user != null && password != null)
		{
			env.put(JMXConnector.CREDENTIALS, new String[]{user, password});
		}

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		ObjectName jmxObjectName = new ObjectName(objectName);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		System.out.print(value);
	}
}
