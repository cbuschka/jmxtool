package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class App implements CommandLineRunner
{
	private Map<String, Command> commandMap = newCommandMap();

	private static Map<String, Command> newCommandMap()
	{
		Map<String, Command> commandMap = new HashMap<>();
		commandMap.put("listMBeans", new ListMBeansCommand());
		commandMap.put("getAttribute", new GetAttributeCommand());
		return commandMap;
	}

	@Autowired
	private PropertiesLoader propertiesLoader;

	@Autowired
	private CommandLineParser commandLineParser;

	public void run(String... args) throws Exception
	{
		if (args.length == 0)
		{
			System.err.println("java -jar jmxtool.jar " + commandMap.keySet()
					.stream()
					.sorted()
					.collect(Collectors.joining("|")));
			System.exit(1);
		}

		try
		{
			Properties props = this.propertiesLoader.load();

			CommandLine commandLine = commandLineParser.parse(args, props);

			String commandName = commandLine.getCommand();
			Command command = commandMap.get(commandName);
			if (command == null)
			{
				throw new InvalidCommandLine("Unknown command " + commandName + ".");
			}
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
