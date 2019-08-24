package com.github.cbuschka.jmxtool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultOutputWriter implements OutputWriter
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS'Z'02:00");

	@Override
	public void startRow(Date date)
	{
		String formattedDate = dateFormat.format(date);
		System.out.print(formattedDate);
	}

	@Override
	public void attribute(String objectName, String attributeName, Object value)
	{
		String formattedLine = String.format(" %s:%s=%s", objectName, attributeName, value);
		System.out.print(formattedLine);
	}

	@Override
	public void endRow()
	{
		System.out.println();
		System.out.flush();
	}
}
