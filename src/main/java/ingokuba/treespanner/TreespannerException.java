package ingokuba.treespanner;

public class TreespannerException extends Exception
{

    private static final long serialVersionUID = 1L;

    public TreespannerException(String format, Object... args)
    {
        super(String.format(format, args));
    }

    public TreespannerException(Throwable cause, String format, Object... args)
    {
        super(String.format(format, args), cause);
    }
}
