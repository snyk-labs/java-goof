## Java Goof

A vulnerable demo application, initially based on [Ben Hassine](https://github.com/benas/)'s [TodoMVC](https://github.com/benas/todolist-mvc). 

The goal of this application is to demonstrate through example how to find, exploit and fix vulnerable Maven packages. 

This repo is still incomplete, a work in progress to support related presentations.


## Build and run Todolist MVC

(from the original README)

*Note that to run locally, you need JDK 8.*

1.  Check out the project source code from github : `git clone https://github.com/snyk/java-goof.git`
2.  Open a terminal and run the following command from root directory : `mvn install`
3.  Choose a web framework to test and run it. For example : `cd todolist-web-struts && mvn tomcat7:run` (note: this example currently only copied the Struts demo)
4.  Browse the following URL : `localhost:8080/`
5.  You can register a new account or login using the following credentials : foo@bar.org / foobar

## Running with docker-compose
```bash
docker-compose up --build
docker-compose down
```

## Datadog profiler agent

### Running locally on a macOS

When running the Goof app locally on a macOS (not inside a container or otherwise), follow this:
* Download and run the agent locally with: 
```
DD_AGENT_MAJOR_VERSION=7 DD_API_KEY=1234 DD_SITE="datadoghq.eu" bash -c "$(curl -L https://s3.amazonaws.com/dd-agent/scripts/install_mac_os.sh)"
```

You can confirm the agent installs and runs successfully with one of these commands:
```
$ datadog-agent status
$ datadog-agent launch-gui
```
* Download the Java library:
```
wget -O dd-java-agent.jar https://dtdg.co/latest-java-tracer
```
* Set the following environment variable to load the agent:
```
export MAVEN_OPTS="-javaagent:dd-java-agent.jar -Ddd.profiling.enabled=true -XX:FlightRecorderOptions=stackdepth=256"
```
* Set the following environment variables to specify service information of the goof app:
```
DD_PROFILING_ENABLED	Boolean	Alternate for -Ddd.profiling.enabled argument. Set to true to enable profiler.
DD_SERVICE	String	Your service name, for example, web-backend.
DD_ENV	String	Your environment name, for example: production.
DD_VERSION	String	The version of your service.
DD_TAGS	String	Tags to apply to an uploaded profile. Must be a list of <key>:<value> separated by commas such as: layer:api, team:intake.
```
* Run the app: `mvn tomcat7:run`

## License
This repo is available released under the [MIT License](http://opensource.org/licenses/mit-license.php/).
# java-goof
