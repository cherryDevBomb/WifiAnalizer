package analyzer.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.primitives.Bytes;

public class ByteUtils {

    public static Stream<String> byteArrayToHexStringStream(byte[] bytes) {
        return Bytes.asList(bytes)
                .stream()
                .map(i -> String.format("%02x", i));
    }

    public static String byteArrayToHexString(byte[] bytes) {
        return byteArrayToHexStringStream(bytes).collect(Collectors.joining(""));
    }

    public static String byteArrayToHexString(byte[] bytes, String delimiter) {
        return byteArrayToHexStringStream(bytes).collect(Collectors.joining(delimiter));
    }

    public static boolean[] intToBinaryArray(int b) {
        boolean[] binaryArray = new boolean[15];
        for (int i = 14; i >= 0; i--) {
            binaryArray[i] = (b % 2 == 1);
            b /= 2;
        }
        return binaryArray;
    }
}
