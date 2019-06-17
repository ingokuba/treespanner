package ingokuba.treespanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class MainTest
{

    private static final String JSON_PATH   = MainTest.class.getClassLoader().getResource("ValidGraph.json").getPath();
    private static final String CUSTOM_PATH = MainTest.class.getClassLoader().getResource("ValidGraph").getPath();

    private static final String DEFAULT_PDU = Integer.toString(10);

    @Test
    public void should_read_json_file()
        throws TreespannerException
    {
        String[] args = {JSON_PATH, DEFAULT_PDU};

        Main.main(args);
    }

    @Test
    public void should_read_custom_file()
        throws TreespannerException
    {
        String[] args = {CUSTOM_PATH, DEFAULT_PDU};

        Main.main(args);
    }

    @Test
    public void should_fail_for_missing_arguments()
    {
        String[] args = {JSON_PATH};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("should be 2"));
        }
    }

    @Test
    public void should_fail_for_too_many_arguments()
    {
        String[] args = {JSON_PATH, DEFAULT_PDU, "test"};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("should be 2"));
        }
    }

    @Test
    public void should_fail_for_invalid_PDUs()
    {
        String[] args = {JSON_PATH, "-1"};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("must be greater than 0"));
        }
    }

    @Test
    public void should_fail_for_invalid_file_type()
    {
        String[] args = {"Test.docx", DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Reader could not be found"));
        }
    }

    @Test
    public void should_fail_for_file_too_long()
    {
        String path = getClass().getClassLoader().getResource("OverlongGraph").getPath();
        String[] args = {path, DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("Line count is greater than"));
        }
    }
}
