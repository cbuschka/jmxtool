package com.github.cbuschka.jmxtool;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListAttributesCommand implements Command
{
	@Override
	public String getDescription()
	{
		return "List MBeans.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String objectName = commandLine.getRequiredOpt("objectName");

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		Map<String, Object> env = new HashMap<>();
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");
		if (user != null && password != null)
		{
			env.put(JMXConnector.CREDENTIALS, new String[]{user, password});
		}

		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		ObjectName jmxObjectName = new ObjectName(objectName);
		MBeanInfo mBeanInfo = jmxConn.getMBeanInfo(jmxObjectName);
		MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
		for (MBeanAttributeInfo jmxAttributeInfo : attributes)
		{
			System.out.println(jmxAttributeInfo.getName());
		}
	}
}
