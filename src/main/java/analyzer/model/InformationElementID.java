package analyzer.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

// TODO remove lookup_map if not needed
public enum InformationElementID {
    SERVICE_SET_IDENTITY(0),
    DIRECT_SEQUENCE_PARAMETER_SET(3);

    private static final Map<Integer, InformationElementID> LOOKUP_MAP;

    static {
        LOOKUP_MAP = Arrays.stream(values()).collect(Collectors.toMap(val -> val.value, val -> val));
    }

    @Getter
    private final int value;

    InformationElementID(int value) {
        this.value = value;
    }

    public static InformationElementID getType(int value) {
        return LOOKUP_MAP.get(value);
    }
}
