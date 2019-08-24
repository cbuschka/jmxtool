package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.Date;

public class GetAttributeCommand implements Command
{
	@Autowired
	private OutputWriterProvider outputWriterProvider;
	@Autowired
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "Get MBean attribute.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		OutputWriter outputWriter = this.outputWriterProvider.getOutputWriter(commandLine.getOpt("outputFormat", "default"));
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String objectName = commandLine.getRequiredOpt("objectName");
		String attributeName = commandLine.getRequiredOpt("attributeName");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");

		MBeanServerConnection jmxConn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		ObjectName jmxObjectName = new ObjectName(objectName);
		Object value = jmxConn.getAttribute(jmxObjectName, attributeName);
		outputWriter.startRow(new Date());
		outputWriter.attribute(objectName, attributeName, value);
		outputWriter.endRow();
	}
}
