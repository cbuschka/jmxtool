package com.github.cbuschka.jmxtool;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

public class OutputWriterProvider
{
	@Autowired
	private DefaultOutputWriter defaultOutputWriter;
	@Autowired
	private JsonOutputWriter jsonOutputWriter;

	public OutputWriter getOutputWriter(String format)
	{
		if ("default".equals(format))
		{
			return this.defaultOutputWriter;
		}
		else if ("json".equals(format))
		{
			return jsonOutputWriter;
		}
		else
		{
			throw new NoSuchElementException("No format " + format + ".");
		}
	}
}
