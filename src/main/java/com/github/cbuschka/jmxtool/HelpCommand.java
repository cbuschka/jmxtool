package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

public class HelpCommand implements Command
{
	@Autowired
	private CommandRegistry commandRegistry;

	@Override
	public String getDescription()
	{
		return "Print help.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		System.out.println("java -jar jmxtool.jar <command> [<opt>*]");
		System.out.println();
		System.out.println("where command is one of:");
		for (String commandName : commandRegistry.getCommandNames())
		{
			Command command = this.commandRegistry.getCommand(commandName);
			System.out.println(String.format("\t%-20s %s", commandName, command.getDescription()));
		}
	}
}
