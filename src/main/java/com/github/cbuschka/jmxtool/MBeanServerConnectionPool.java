package com.github.cbuschka.jmxtool;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MBeanServerConnectionPool
{
	private Map<Key, MBeanServerConnection> connections = new HashMap<>();

	public MBeanServerConnection getConnection(String serviceUrl, String user, String password) throws IOException
	{
		Key connKey = new Key(serviceUrl, user, password);
		MBeanServerConnection conn = this.connections.get(connKey);
		if (conn == null || !isAlive(conn))
		{
			conn = open(serviceUrl, user, password);
			this.connections.put(connKey, conn);
		}

		return conn;
	}

	private boolean isAlive(MBeanServerConnection conn)
	{
		try
		{
			Set<ObjectInstance> objectInstances = conn.queryMBeans(new ObjectName("*:*"), null);
			return objectInstances != null;
		}
		catch (IOException | MalformedObjectNameException ex)
		{
			return false;
		}
	}

	private MBeanServerConnection open(String serviceUrl, String user, String password) throws IOException
	{
		JMXServiceURL jmxServiceUrl = new JMXServiceURL(serviceUrl);
		Map<String, Object> env = new HashMap<>();
		if (user != null && password != null)
		{
			env.put(JMXConnector.CREDENTIALS, new String[]{user, password});
		}
		JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceUrl, env);
		return jmxConnector.getMBeanServerConnection();
	}

	private static class Key
	{
		private String serviceUrl;
		private String user;
		private String password;

		public Key(String serviceUrl, String user, String password)
		{
			this.serviceUrl = serviceUrl;
			this.user = user;
			this.password = password;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Key key = (Key) o;
			return serviceUrl.equals(key.serviceUrl) &&
					Objects.equals(user, key.user) &&
					Objects.equals(password, key.password);
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(serviceUrl, user, password);
		}
	}
}
