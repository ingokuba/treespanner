package ingokuba.treespanner;

public enum FileType
{
    /**
     * Json format. Ends with '.json'
     */
    JSON,
    /**
     * Custom format. Ends with '.txt'
     */
    TXT,
    /**
     * Custom format. Ends with '.custom' or no file extension.
     */
    CUSTOM,
    /**
     * Unknown file type
     */
    UNKNOWN;

    public static FileType fromString(String value)
    {
        if (value == null || value.isEmpty()) {
            return CUSTOM;
        }
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
