package ingokuba.treespanner.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Path
{

    private Node node;
    private int  cost;
}
