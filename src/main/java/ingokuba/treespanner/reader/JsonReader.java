package ingokuba.treespanner.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import ingokuba.treespanner.TreespannerException;
import ingokuba.treespanner.object.Graph;

public class JsonReader
    implements GraphReader
{

    /**
     * Generate a {@link Graph} object from the json file.
     */
    @Override
    public Graph read(Path path)
        throws TreespannerException
    {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return new Gson().fromJson(reader, Graph.class);
        } catch (IOException e) {
            throw new TreespannerException("Could not read from file '%s'", path);
        }
    }
}
