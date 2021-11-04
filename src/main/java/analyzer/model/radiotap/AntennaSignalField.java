package analyzer.model.radiotap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AntennaSignalField {

    private int dBmSignalPower;

    public static AntennaSignalField parse(byte[] bytes) {
        return AntennaSignalField.builder()
                .dBmSignalPower(bytes[0])
                .build();
    }
}
