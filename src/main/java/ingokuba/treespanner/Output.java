package ingokuba.treespanner;

import static java.util.logging.Level.ALL;

import java.io.Console;
import java.util.logging.Logger;

public class Output
{

    private static final Logger LOGGER = Logger.getLogger(Output.class.getName());

    private Output()
    {
    }

    public static void print(String format, Object... args)
    {
        Console console = System.console();
        if (console == null) {
            String message = String.format(format, args);
            LOGGER.log(ALL, message);
        }
        else {
            console.printf(format, args);
        }
    }
}
