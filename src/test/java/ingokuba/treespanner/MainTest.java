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
    public void should_fail_check_for_duplicate_node_name()
    {
        String path = getClass().getClassLoader().getResource("DuplicateName.json").getPath();
        String[] args = {path, DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("name 'A' is not unique"));
        }
    }

    @Test
    public void should_fail_check_for_duplicate_node_id()
    {
        String path = getClass().getClassLoader().getResource("DuplicateId.json").getPath();
        String[] args = {path, DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("id '1' is not unique"));
        }
    }

    @Test
    public void should_fail_check_for_self_reference()
    {
        String path = getClass().getClassLoader().getResource("SelfReference.json").getPath();
        String[] args = {path, DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("references itself"));
        }
    }

    @Test
    public void should_fail_check_for_invalid_link()
    {
        String path = getClass().getClassLoader().getResource("InvalidLink.json").getPath();
        String[] args = {path, DEFAULT_PDU};

        try {
            Main.main(args);
            fail();
        } catch (TreespannerException e) {
            assertThat(e.getMessage(), containsString("doesn't exist"));
        }
    }
}
