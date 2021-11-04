package analyzer.model.radiotap;

import analyzer.util.ByteUtils;
import lombok.Builder;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Data
@Builder
public class RadiotapHeader {

    private byte version;
    private byte padding;
    private short length;
    private boolean[] present; // bitmask of 32 bits

    public static RadiotapHeader parse(byte[] bytes) {
        return RadiotapHeader.builder()
                .version(bytes[0])
                .padding(bytes[1])
                .length(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort())
                .present(ByteUtils.byteArrayToBooleanArray(Arrays.copyOfRange(bytes, 4, 36)))
                .build();
    }
}
