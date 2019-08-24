package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

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

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		ObjectName jmxObjectName = new ObjectName(objectName);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		System.out.print(value);
	}
}
