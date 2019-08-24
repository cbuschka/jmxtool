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
	private MBeanServerConnectionPool mBeanServerConnectionPool;

	@Override
	public String getDescription()
	{
		return "Poll and print attributes.";
	}

	@Override
	public void execute(CommandLine commandLine) throws Exception
	{
		String serviceUrl = commandLine.getRequiredOpt("serviceUrl");
		String user = commandLine.getOpt("user");
		String password = commandLine.getOpt("password");
		int sleepSeconds = commandLine.getIntOpt("sleep", 1);

		String attributesOpt = commandLine.getRequiredOpt("attributes");
		List<AttributeLocator> attributeLocators = parseAttributeLocators(attributesOpt);

		MBeanServerConnection conn = mBeanServerConnectionPool.getConnection(serviceUrl, user, password);
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS'Z'02:00");
		while (true)
		{
			System.out.print(String.format("%s", dateFormat.format(new Date())));
			for (AttributeLocator attributeLocator : attributeLocators)
			{
				Object value = conn.getAttribute(attributeLocator.getObjectName(), attributeLocator.getAttributeName());
				System.out.print(String.format(" %s:%s=%s", attributeLocator.getObjectName(), attributeLocator.getAttributeName(), value));
			}

			System.out.println();
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
