package com.github.cbuschka.jmxtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(new Class[]{
				AppConfig.class, VersionConfig.class
		}, args);
	}
}
