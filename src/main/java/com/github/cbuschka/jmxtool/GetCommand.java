package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class GetCommand
{
	private CommandLine commandLine;

	public GetCommand(CommandLine commandLine)
	{
		this.commandLine = commandLine;
	}

	public void execute() throws Exception
	{

		if (!this.commandLine.getCommand().equals("get"))
		{
			throw new IllegalArgumentException("Command must be get.");
		}

		String serviceUrl = this.commandLine.getRequiredOpt("service-url");
		String objectName = this.commandLine.getRequiredOpt("object-name");
		String attributeName = this.commandLine.getRequiredOpt("attribute-name");

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		ObjectName jmxObjectName = new ObjectName(objectName);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		System.out.print(value);
	}
}
