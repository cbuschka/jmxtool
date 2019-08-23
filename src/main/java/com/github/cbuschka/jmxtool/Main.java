package com.github.cbuschka.jmxtool;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		if (args.length == 0)
		{
			System.err.println("java -jar jmxtool.jar get --service-url=service-url --object-name=domain:key=value,key2=vallue --attributeName=aKey");
			System.exit(1);
		}

		try
		{
			CommandLine commandLine = CommandLine.parse(args);

			GetCommand getCommand = new GetCommand(commandLine);
			getCommand.execute();
		}
		catch (InvalidCommandLine ex)
		{
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		finally
		{
			System.out.flush();
			System.err.flush();
		}
	}
}
