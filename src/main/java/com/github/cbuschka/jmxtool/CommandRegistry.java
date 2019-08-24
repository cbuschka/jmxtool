package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CommandRegistry
{
	private Map<String, Command> commandMap = new LinkedHashMap<>();

	@Autowired
	private HelpCommand helpCommand;
	@Autowired
	private VersionCommand versionCommand;
	@Autowired
	private GetAttributeCommand getAttributeCommand;
	@Autowired
	private ListMBeansCommand listMBeansCommand;
	@Autowired
	private ListAttributesCommand listAttributesCommand;

	@PostConstruct
	private void init()
	{
		commandMap.put("help", helpCommand);
		commandMap.put("listMBeans", listMBeansCommand);
		commandMap.put("listAttributes", listAttributesCommand);
		commandMap.put("getAttribute", getAttributeCommand);
		commandMap.put("version", versionCommand);
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
