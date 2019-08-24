package com.github.cbuschka.jmxtool;

import java.util.Properties;

public class Main
{
	private PropertiesLoader propertiesLoader = new PropertiesLoader();

	private CommandLineParser commandLineParser = new CommandLineParser();

	public static void main(String[] args) throws Exception
	{
		if (args.length == 0)
		{
			System.err.println("java -jar jmxtool.jar getAttribute --serviceUrl=service-url --objectName=domain:key=value,key2=value --attributeName=aKey");
			System.exit(1);
		}

		new Main().run(args);
	}

	private void run(String[] args) throws Exception
	{
		try
		{
			Properties props = this.propertiesLoader.load();

			CommandLine commandLine = commandLineParser.parse(args, props);

			GetAttributeCommand getAttributeCommand = new GetAttributeCommand(commandLine);
			getAttributeCommand.execute();
		}
		catch (InvalidCommandLine ex)
		{
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		finally
		{
			System.out.flush();
			System.err.flush();
		}
	}
}
