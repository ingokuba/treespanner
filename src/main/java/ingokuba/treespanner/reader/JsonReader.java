package ingokuba.treespanner.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import ingokuba.treespanner.object.Graph;

public class JsonReader
    implements GraphReader
{

    private static final Logger LOGGER = Logger.getLogger(JsonReader.class.getName());

    /**
     * Generate a {@link Graph} object from the json file.
     */
    @Override
    public Graph read(Path path)
    {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return new Gson().fromJson(reader, Graph.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read from file '" + path + "'", e);
            return null;
        }
    }
}
