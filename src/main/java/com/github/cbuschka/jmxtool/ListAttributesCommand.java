package com.github.cbuschka.jmxtool;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
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
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl);
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
