package com.github.cbuschka.jmxtool;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CommandRegistry
{
	private Map<String, Command> commandMap = new LinkedHashMap<>();

	@PostConstruct
	private void init()
	{
		commandMap.put("listMBeans", new ListMBeansCommand());
		commandMap.put("getAttribute", new GetAttributeCommand());
	}

	public List<String> getCommandNames()
	{
		return new ArrayList<>(this.commandMap.keySet());
	}

	public Command getCommand(String commandName)
	{
		Command command = this.commandMap.get(commandName);
		if (command == null)
		{
			throw new NoSuchElementException("No command " + commandName + ".");
		}
		return command;
	}

	public boolean isCommand(String commandName)
	{
		return this.commandMap.containsKey(commandName);
	}
}
