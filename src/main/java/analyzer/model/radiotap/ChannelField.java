package analyzer.model.radiotap;

import lombok.Builder;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Data
@Builder
public class ChannelField {

    private short frequency;
    private byte[] flags;

    public static ChannelField parse(byte[] bytes) {
        return ChannelField.builder()
                .frequency(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort())
                .flags(Arrays.copyOfRange(bytes, 2, 4))
                .build();
    }
}
