package analyzer.model.frame;

import java.nio.ByteBuffer;
import java.util.Arrays;

import analyzer.util.ByteUtils;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixedParameters {

    private long timestamp;
    private short beaconInterval;
    private boolean[] capabilityInfo;

    public static FixedParameters parse(byte[] bytes) {
        return FixedParameters.builder()
                .timestamp(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 8)).getLong())
                .beaconInterval(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 8, 10)).getShort())
//                .capabilityInfo(ByteUtils.intToBinaryArray(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 10, 12)).getShort()))
                .capabilityInfo(ByteUtils.byteArrayToBooleanArray(Arrays.copyOfRange(bytes, 10, 12)))
                .build();
    }
}
