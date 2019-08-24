package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

public class VersionCommand implements Command
{
	@Autowired
	private VersionConfig versionConfig;

	@Override
	public String getDescription()
	{
		return "Print version info.";
	}

	public void execute(CommandLine commandLine) throws Exception
	{
		System.out.println(String.format("%s (%s)", versionConfig.getProjectVersion(), versionConfig.getCommitish()));
	}
}
