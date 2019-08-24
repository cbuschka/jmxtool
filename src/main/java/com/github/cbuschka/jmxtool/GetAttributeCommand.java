package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class GetAttributeCommand
{
	private CommandLine commandLine;

	public GetAttributeCommand(CommandLine commandLine)
	{
		this.commandLine = commandLine;
	}

	public void execute() throws Exception
	{

		if (!this.commandLine.getCommand().equals("getAttribute"))
		{
			throw new IllegalArgumentException("Command must be getAttribute.");
		}

		String serviceUrl = this.commandLine.getRequiredOpt("serviceUrl");
		String objectName = this.commandLine.getRequiredOpt("objectName");
		String attributeName = this.commandLine.getRequiredOpt("attributeName");

		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		ObjectName jmxObjectName = new ObjectName(objectName);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl);
		MBeanServerConnection jmxConn = jmxConnector.getMBeanServerConnection();
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		System.out.print(value);
	}
}
