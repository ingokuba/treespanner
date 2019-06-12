package ingokuba.treespanner;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.WARNING;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import ingokuba.treespanner.object.SpanningTree;
import ingokuba.treespanner.parser.CustomReader;
import ingokuba.treespanner.parser.GraphReader;
import ingokuba.treespanner.parser.JsonReader;

public class Main
{

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        String fileName = args[0];
        Path path = Paths.get(fileName);
        FileType fileType = getFileExtension(path.getFileName().toString());
        GraphReader reader = null;
        switch (fileType) {
        case JSON:
            reader = new JsonReader();
            break;
        case CUSTOM:
            reader = new CustomReader();
            break;
        case UNKNOWN:
            LOGGER.log(WARNING, "File type is not supported.");
        }
        if (reader == null) {
            LOGGER.log(WARNING, "Reader could not be found for file type {0}.", fileType);
            return;
        }
        SpanningTree tree = new TreeSpanner(reader.read(path)).getSpanningTree();
        if (tree == null) {
            LOGGER.log(WARNING, "Could not generate tree.");
        }
        else {
            LOGGER.log(FINE, "Spanning tree: {0}", tree);
        }
    }

    /**
     * Get the {@link FileType} based on the files name.
     * 
     * @param fileName e.g. Example.csv
     * @return e.g. {@link FileType#CUSTOM}
     */
    private static FileType getFileExtension(String fileName)
    {
        String[] parts = fileName.split("\\.");
        return FileType.fromString(parts.length > 1 ? parts[parts.length - 1] : "");
    }
}
