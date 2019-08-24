package com.github.cbuschka.jmxtool;

import java.util.Date;

public interface OutputWriter
{
	void startRow(Date date);

	void attribute(String objectName, String attributeName, Object value);

	void endRow();
}
