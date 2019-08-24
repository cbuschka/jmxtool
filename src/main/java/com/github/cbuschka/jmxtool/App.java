package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Properties;
import java.util.stream.Collectors;

public class App implements CommandLineRunner
{
	@Autowired
	private PropertiesLoader propertiesLoader;

	@Autowired
	private CommandLineParser commandLineParser;

	@Autowired
	private CommandRegistry commandRegistry;

	public void run(String... args) throws Exception
	{
		try
		{
			Properties props = this.propertiesLoader.load();

			CommandLine commandLine = commandLineParser.parse(args, props);
			if (commandLine.getCommand() == null)
			{
				commandLine.setCommand("help");
			}

			String commandName = commandLine.getCommand();
			Command command = commandRegistry.getCommand(commandName);
			command.execute(commandLine);
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
