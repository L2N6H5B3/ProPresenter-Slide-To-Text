package StageToText.model;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.0.0
 */

public class Field {

    private final String identifier;
    private final String data;

    public Field(String identifier, String data) {
        this.identifier = identifier;
        this.data = data;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getData() {
        return data;
    }
}
