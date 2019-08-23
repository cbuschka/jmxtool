package com.github.cbuschka.jmxtool;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommandLineTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String[] args;

	private CommandLine commandLine;

	@Test
	public void parsesKeyValue() throws InvalidCommandLine
	{
		givenArgs("--key=value");

		whenParsed();

		thenOptsAre("key", "value");
	}

	@Test
	public void parsesKeyFlag() throws InvalidCommandLine
	{
		givenArgs("--key");

		whenParsed();

		thenOptsAre("key", "true");
	}

	@Test
	public void rejectsSingleDash() throws InvalidCommandLine
	{
		expectedException.expect(InvalidCommandLine.class);

		givenArgs("-key");

		whenParsed();
	}

	private void thenOptsAre(String key, String value)
	{
		Map<String, Object> expectedOpts = new HashMap<>();
		expectedOpts.put(key, value);
		assertThat(this.commandLine.getOpts(), is(expectedOpts));
	}

	private void whenParsed() throws InvalidCommandLine
	{
		this.commandLine = CommandLine.parse(args);
	}

	private void givenArgs(String... args)
	{
		this.args = args;
	}
}
