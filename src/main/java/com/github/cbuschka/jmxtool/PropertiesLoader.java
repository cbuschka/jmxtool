package com.github.cbuschka.jmxtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader
{
	public Properties load() throws IOException
	{
		Properties props = new Properties();

		String userHomePath = System.getProperty("user.home");
		if (userHomePath != null)
		{
			File userHome = new File(userHomePath);
			File homePropsFile = new File(userHome, ".jmxtool.properties");
			if (homePropsFile.canRead())
			{
				Properties homeProps = new Properties();
				try (InputStream in = new FileInputStream(homePropsFile))
				{
					homeProps.load(in);
					props.putAll(homeProps);
				}
			}
		}

		props.putAll(System.getProperties());

		return props;
	}
}
