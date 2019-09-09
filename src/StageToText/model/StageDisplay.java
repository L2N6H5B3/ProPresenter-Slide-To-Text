package StageToText.model;

/**
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.0.0
 */

import java.util.ArrayList;
import java.util.List;

public class StageDisplay {

    private final List<Field> fields;

    private StageDisplay(Builder builder) {
        fields = builder.fields;
    }

    public static Builder newStageDisplayBuilder() {
        return new Builder();
    }

    public Field getField(String fieldIdentifier) {
        for (Field field : fields) {
            if (fieldIdentifier.equals(field.getIdentifier())) {
               return field;
            }
        }
        return null;
    }

    public String getData(String fieldIdentifier) {
        Field field = getField(fieldIdentifier);
        return field == null ? "" : field.getData();
    }

    public static class Builder {
        List<Field> fields;

        private Builder() {}

        public Builder addField(Field field) {
            if (fields == null) {
                fields = new ArrayList<>();
            }
            fields.add(field);
            return this;
        }

        public StageDisplay build() {
            return new StageDisplay(this);
        }
    }
}
