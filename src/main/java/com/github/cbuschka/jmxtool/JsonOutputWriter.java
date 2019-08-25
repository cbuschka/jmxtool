package com.github.cbuschka.jmxtool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonOutputWriter implements OutputWriter
{
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSS'Z'Z");

	@Autowired
	private ObjectMapper objectMapper;

	private Map<String, Object> row;

	@Override
	public void startRow(Date date)
	{
		this.row = new HashMap<>();
		String formattedDate = dateFormat.format(date);
		this.row.put("timestamp", formattedDate);
	}

	@Override
	public void attribute(String objectName, String attributeName, Object value)
	{
		this.row.put(objectName + ":" + attributeName, value);
	}

	@Override
	public void endRow()
	{
		try
		{
			System.out.println(this.objectMapper.writer().writeValueAsString(this.row));
			System.out.flush();
			this.row = null;
		}
		catch (JsonProcessingException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
