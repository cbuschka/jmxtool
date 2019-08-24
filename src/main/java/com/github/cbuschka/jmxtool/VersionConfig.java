package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:git.properties", encoding = "UTF-8")
public class VersionConfig
{
	@Value("${git.commit.id:UNKNOWN}")
	private String commitish;

	@Value("${git.build.version:UNKNOWN}")
	private String projectVersion;

	public String getCommitish()
	{
		return commitish;
	}

	public String getProjectVersion()
	{
		return projectVersion;
	}
}
