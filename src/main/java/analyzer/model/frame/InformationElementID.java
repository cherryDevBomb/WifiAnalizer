package analyzer.model.frame;

import lombok.Getter;

public enum InformationElementID {

    SERVICE_SET_IDENTITY(0),
    DIRECT_SEQUENCE_PARAMETER_SET(3);

    @Getter
    private final int value;

    InformationElementID(int value) {
        this.value = value;
    }
}
