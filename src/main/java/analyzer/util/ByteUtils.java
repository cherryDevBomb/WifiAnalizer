package analyzer.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.primitives.Bytes;

public class ByteUtils {

    public static Stream<String> byteArrayToHexStringStream(byte[] bytes) {
        return Bytes.asList(bytes)
                .stream()
                // .map(Byte::toUnsignedInt)
                // .map(Integer::toHexString)
                .map(i -> String.format("%02x", i));
    }

    public static String byteArrayToHexString(byte[] bytes) {
        return byteArrayToHexStringStream(bytes).collect(Collectors.joining(""));
    }

    public static String byteArrayToHexString(byte[] bytes, String delimiter) {
        return byteArrayToHexStringStream(bytes).collect(Collectors.joining(delimiter));
    }
}
