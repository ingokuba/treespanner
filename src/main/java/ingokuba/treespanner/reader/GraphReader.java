package ingokuba.treespanner.reader;

import java.nio.file.Path;

import ingokuba.treespanner.object.Graph;

public interface GraphReader
{

    public Graph read(Path path);

}
