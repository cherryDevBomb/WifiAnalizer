package analyzer.util;

import com.google.common.primitives.Bytes;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static boolean[] byteArrayToBooleanArray(byte[] bytes) {
        int lengthInBits = bytes.length * 8;
        boolean[] booleanArray = new boolean[lengthInBits];
        for (int i = bytes.length - 1; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                booleanArray[i * 8 + j] = (bytes[i] & (1 << j)) != 0;
            }
        }
        return booleanArray;
    }

    // TODO remove after testing
    public static void main(String[] args) {
        byte[] arr = {0x70, 0x51}; //0111 0000 0101 0001
        boolean[] res = byteArrayToBooleanArray(arr);
        System.out.println(Arrays.toString(res));
    }
}
