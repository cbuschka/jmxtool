package com.github.cbuschka.jmxtool;

import java.util.HashMap;
import java.util.Map;

public class CommandLine
{
	String command;
	Map<String, Object> opts = new HashMap<>();

	public Map<String, Object> getOpts()
	{
		return opts;
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

	public void setCommand(String command)
	{
		this.command = command;
	}

	public String getOpt(String key)
	{
		return getOpt(key, null);
	}

	public String getOpt(String key, String defaultValue)
	{
		return (String) this.opts.getOrDefault(key, defaultValue);
	}
}
