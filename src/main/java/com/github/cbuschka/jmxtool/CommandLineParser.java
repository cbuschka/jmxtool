package com.github.cbuschka.jmxtool;

import java.util.Map;
import java.util.Properties;

public class CommandLineParser
{
	public CommandLine parse(String[] args, Properties props) throws InvalidCommandLine
	{
		CommandLine commandLine = new CommandLine();
		for (int i = 0; i < args.length; ++i)
		{
			String arg = args[i];
			if (isCommand(arg))
			{
				parseCommand(commandLine, arg);
			}
			else if (isOption(arg))
			{
				parseOpt(commandLine, arg);
			}
			else
			{
				throw new InvalidCommandLine("Invalid arg " + arg + ".");
			}
		}

		addUndefinedProps(commandLine, props);

		return commandLine;
	}

	private void addUndefinedProps(CommandLine commandLine, Properties props)
	{
		for (Map.Entry<Object, Object> entry : props.entrySet())
		{
			Object keyObj = entry.getKey();
			if (!(keyObj instanceof String))
			{
				continue;
			}
			String key = (String) keyObj;

			if (!commandLine.opts.containsKey(key))
			{
				commandLine.opts.put(key, entry.getValue());
			}
		}
	}

	private void parseOpt(CommandLine commandLine, String arg)
	{
		int eqPos = arg.indexOf('=');
		String key;
		String value;
		if (eqPos == -1)
		{
			key = arg.substring("--".length());
			value = "true";
		}
		else
		{
			key = arg.substring("--".length(), eqPos);
			value = arg.substring(eqPos + 1);
		}
		commandLine.opts.put(key, value);
	}

	private void parseCommand(CommandLine commandLine, String arg) throws InvalidCommandLine
	{
		if (commandLine.command != null)
		{
			throw new InvalidCommandLine("Only single command allowed.");
		}

		commandLine.command = arg;
	}

	private boolean isOption(String arg)
	{
		return arg.startsWith("--");
	}

	private boolean isCommand(String arg)
	{
		return !arg.startsWith("-");
	}
}
