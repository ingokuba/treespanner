package ingokuba.treespanner.object;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Node
{

    private String  name;
    private Integer id;
    private Node    nextHop;
    private int     messageCount = 0;

    /**
     * Increment the message count.
     * 
     * @return New message count
     */
    public int increaseMessageCount()
    {
        return messageCount++;
    }
}
