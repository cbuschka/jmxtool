# jmxtool - Command line tool to easily access jmx information

## Usage

### Get an MBean attribute
```bash
jmxtool getAttribute \
	--user=me --password=secret \
	--serviceUrl=service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi \
	--objectName=Catalina:type=Manager,context=/,host=localhost \
	--attributeName=activeSessions
```

### Get command list
```bash
jmxtool help
```

## Config, user/password, default values
Any option can be added to ${HOME}/.jmxtool.properties so its value will be applied as a default for this option.

Example:
```
user=admin
password=secret
serviceUrl=service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi
```

## Author
Written 2019 by [Cornelius Buschka](https://github.com/cbuschka).

## License
[Apache License, Version 2.0](./license.txt)
