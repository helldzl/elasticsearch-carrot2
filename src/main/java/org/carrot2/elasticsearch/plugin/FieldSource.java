package org.carrot2.elasticsearch.plugin;

public enum FieldSource {
    HIGHLIGHT("highlight."),
    FIELD("fields."),
    SOURCE("_source.");

    /**
     * Field specification prefix for this source.
     */
    private final String fieldSpecPrefix;

    static class ParsedFieldSource {
        final FieldSource source;
        final String fieldName;
        
        public ParsedFieldSource(FieldSource source, String fieldName) {
            this.source = source;
            this.fieldName = fieldName;
        }
    }

    /* */
    static ParsedFieldSource parseSpec(String fieldSourceSpec) {
        if (fieldSourceSpec != null) {
            for (FieldSource fs : cachedByOrdinal) {
                if (fieldSourceSpec.startsWith(fs.fieldSpecPrefix)) {
                    return new ParsedFieldSource(fs, fieldSourceSpec.substring(fs.fieldSpecPrefix.length()));
                }
            }
        }
        return null;
    }
    
    static FieldSource [] cachedByOrdinal = values();
    static FieldSource fromOrdinal(int ordinal) {
        return cachedByOrdinal[ordinal];
    }

    private FieldSource(String fieldSpecPrefix) {
        this.fieldSpecPrefix = fieldSpecPrefix; 
    }
}