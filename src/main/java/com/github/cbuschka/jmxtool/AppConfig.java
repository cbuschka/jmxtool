package com.github.cbuschka.jmxtool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
	@Bean
	public VersionCommand versionCommand()
	{
		return new VersionCommand();
	}

	@Bean
	public HelpCommand helpCommand()
	{
		return new HelpCommand();
	}

	@Bean
	public CommandRegistry commandRegistry()
	{
		return new CommandRegistry();
	}

	@Bean
	public CommandLineRunner commandLineRunner()
	{
		return new App();
	}

	@Bean
	public PropertiesLoader propertiesLoader()
	{
		return new PropertiesLoader();
	}

	@Bean
	public CommandLineParser commandLineParser()
	{
		return new CommandLineParser();
	}
}
