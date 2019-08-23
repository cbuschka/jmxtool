package com.github.cbuschka.jmxtool;

import java.util.HashMap;
import java.util.Map;

public class CommandLine
{
	private String command;
	private Map<String, Object> opts = new HashMap<>();

	public Map<String, Object> getOpts()
	{
		return opts;
	}

	public static CommandLine parse(String[] args) throws InvalidCommandLine
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

		return commandLine;
	}

	private static void parseOpt(CommandLine commandLine, String arg)
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

	private static void parseCommand(CommandLine commandLine, String arg) throws InvalidCommandLine
	{
		if (commandLine.command != null)
		{
			throw new InvalidCommandLine("Only single command allowed.");
		}

		commandLine.command = arg;
	}

	private static boolean isOption(String arg)
	{
		return arg.startsWith("--");
	}

	private static boolean isCommand(String arg)
	{
		return !arg.startsWith("-");
	}

	public String getCommand()
	{
		return this.command;
	}

	public String getRequiredOpt(String key)
	{
		check(key);

		return (String) this.opts.get(key);
	}

	public void check(String key)
	{
		if (!this.opts.containsKey(key))
		{
			throw new IllegalArgumentException("Option --" + key + " is required.");
		}
	}
}
