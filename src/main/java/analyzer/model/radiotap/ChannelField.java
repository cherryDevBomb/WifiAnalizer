package analyzer.model.radiotap;

import lombok.Builder;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

import analyzer.util.ByteUtils;

import com.google.common.primitives.Bytes;

@Data
@Builder
public class ChannelField {

    private int frequency;
    private byte[] flags;

    public static ChannelField parse(byte[] bytes) {
        System.out.println("frequency: : " + ByteUtils.byteArrayToHexString(Arrays.copyOfRange(bytes, 0, 2)));
        byte[] frequencyBytes = Arrays.copyOfRange(bytes, 0, 2);

        return ChannelField.builder()
                .frequency(Short.toUnsignedInt(ByteBuffer.wrap(Bytes.toArray(Collections.reverse(Arrays.asList(frequencyBytes)))).getShort()))
                .flags(Arrays.copyOfRange(bytes, 2, 4))
                .build();
    }
}
