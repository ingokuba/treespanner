package ingokuba.treespanner;

import org.junit.jupiter.api.Test;

public class MainTest
{

    @Test
    public void should_read_json_file()
    {
        String path = getClass().getClassLoader().getResource("Test.json").getPath();
        String[] args = {path};

        Main.main(args);
    }

    @Test
    public void should_read_custom_file()
    {
        String path = getClass().getClassLoader().getResource("TestGraph").getPath();
        String[] args = {path};

        Main.main(args);
    }
}
