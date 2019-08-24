package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TailCommand implements Command
{
	@Autowired
	private OutputWriterProvider outputWriterProvider;
	@Autowired
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "Poll and print attributes.";
	}

	@Override
	public void execute(CommandLine commandLine) throws Exception
	{
		OutputWriter outputWriter = this.outputWriterProvider.getOutputWriter(commandLine.getOpt("outputFormat", "default"));
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");
		int sleepSeconds = commandLine.getIntOpt("sleep", 1);

		String attributesOpt = commandLine.getRequiredOpt("attributes");
		List<AttributeLocator> attributeLocators = parseAttributeLocators(attributesOpt);

		MBeanServerConnection conn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		while (true)
		{
			outputWriter.startRow(new Date());
			for (AttributeLocator attributeLocator : attributeLocators)
			{
				Object value = conn.getAttribute(attributeLocator.getObjectName(), attributeLocator.getAttributeName());
				outputWriter.attribute(attributeLocator.getObjectName().toString(), attributeLocator.getAttributeName(), value);
			}

			outputWriter.endRow();
			Thread.sleep(sleepSeconds * 1000);
		}
	}

	private List<AttributeLocator> parseAttributeLocators(String attributesOpt) throws MalformedObjectNameException, InvalidCommandLine
	{
		List<AttributeLocator> attributeLocators = new ArrayList<>();
		String[] parts = attributesOpt.split(";");
		for (String part : parts)
		{
			int lastColonPos = part.lastIndexOf(":");
			if (lastColonPos == -1)
			{
				throw new InvalidCommandLine("Attributes must be of the form <objectName>:<attributeName>[;<objectName>:<attributeName>]*.");
			}
			String objectName = part.substring(0, lastColonPos);
			String attributeName = part.substring(lastColonPos + 1);
			attributeLocators.add(new AttributeLocator(objectName, attributeName));
		}

		return attributeLocators;
	}
}
