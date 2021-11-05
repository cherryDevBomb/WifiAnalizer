package analyzer.model.radiotap;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.google.common.primitives.Bytes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelField {

    private int frequency;
    private byte[] flags;

    public static ChannelField parse(byte[] bytes) {
        // treat bytes received in little endian
        byte[] frequencyBytes = Arrays.copyOfRange(bytes, 0, 2);
        Bytes.reverse(frequencyBytes);

        return ChannelField.builder()
                .frequency(Short.toUnsignedInt(ByteBuffer.wrap(frequencyBytes).getShort()))
                .flags(Arrays.copyOfRange(bytes, 2, 4))
                .build();
    }
}
