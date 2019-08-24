package com.github.cbuschka.jmxtool;

public interface Command
{
	void execute(CommandLine commandLine) throws Exception;

	String getDescription();
}
