# jmxtool - Command line tool to easily access jmx information

## Usage

```bash
jmxtool get \
	--service-url=service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi \
	--object-name=Catalina:type=Manager,context=/,host=localhost \
	--attributeName=activeSessions
```

## Author
Written 2019 by [Cornelius Buschka](https://github.com/cbuschka).

## License
[MIT](./license.txt)
