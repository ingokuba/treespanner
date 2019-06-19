package ingokuba.treespanner;

import java.nio.file.Path;
import java.nio.file.Paths;

import ingokuba.treespanner.object.SpanningTree;
import ingokuba.treespanner.reader.CustomReader;
import ingokuba.treespanner.reader.GraphReader;
import ingokuba.treespanner.reader.JsonReader;

public class Main
{

    public static void main(String[] args)
        throws TreespannerException
    {
        if (args.length != 2) {
            throw new TreespannerException("Number of arguments is %d, but should be 2.", args.length);
        }
        String fileName = args[0];
        Path path = Paths.get(fileName);
        FileType fileType = getFileExtension(path.getFileName().toString());
        GraphReader reader = getReader(fileType);
        if (reader == null) {
            throw new TreespannerException("Reader could not be found for file type %s.", fileType);
        }
        int minPDU = Integer.parseInt(args[1]);
        if (minPDU < 0) {
            throw new TreespannerException("Minimum PDUs must be greater than 0, but was %d.", minPDU);
        }
        SpanningTree tree = new TreeSpanner(reader.read(path)).getSpanningTree(minPDU);
        Output.print(tree.toString());
    }

    /**
     * Get the reader for a specific file type.
     * 
     * @param fileType of the file to read
     * @return {@link GraphReader} for the file or null, if unsupported
     */
    private static GraphReader getReader(FileType fileType)
    {
        switch (fileType) {
        case JSON:
            return new JsonReader();
        case TXT:
        case CUSTOM:
            return new CustomReader();
        default:
            return null;
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
