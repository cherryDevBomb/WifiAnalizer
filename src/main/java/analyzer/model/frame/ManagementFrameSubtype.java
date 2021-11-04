package analyzer.model.frame;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ManagementFrameSubtype {
    ASSOCIATION_REQUEST(0x00),
    ASSOCIATION_RESPONSE(0x01),
    REASSOCIATION_REQUEST(0x02),
    REASSOCIATION_RESPONSE(0x03),
    PROBE_REQUEST(0x04),
    PROBE_RESPONSE(0x05),
    BEACON(0x08),
    ATIM(0x09),
    DISASSOCIATION(0x0a),
    AUTHENTICATION(0x0b),
    DEAUTHENTICATION(0x0c),
    ACTION(0x0d),
    NACK(0x0e),
    RESERVED(0x0f);

    private static final Map<Integer, ManagementFrameSubtype> LOOKUP_MAP;

    static {
        LOOKUP_MAP = Arrays.stream(values()).collect(Collectors.toMap(val -> val.value, val -> val));
    }

    private final int value;

    ManagementFrameSubtype(int value) {
        this.value = value;
    }

    public static ManagementFrameSubtype getType(int value) {
        return LOOKUP_MAP.get(value);
    }
}
