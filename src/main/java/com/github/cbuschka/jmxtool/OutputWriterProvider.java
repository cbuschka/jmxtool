package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

public class OutputWriterProvider
{
	@Autowired
	private DefaultOutputWriter defaultOutputWriter;

	public OutputWriter getOutputWriter()
	{
		return this.defaultOutputWriter;
	}
}
