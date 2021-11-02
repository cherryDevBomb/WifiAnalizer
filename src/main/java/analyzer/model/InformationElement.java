package analyzer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InformationElement {

    private byte elementId;
    private byte length;
    private byte[] body;

    public static List<InformationElement> parseList(byte[] bytes) {
        List<InformationElement> parsedElements = new ArrayList<>();

        int offset = 0;
        while (offset < bytes.length) {
            byte id = bytes[offset++];
            byte len = bytes[offset++];
            byte[] infoBody = Arrays.copyOfRange(bytes, offset, offset + len);

            offset += len;

            InformationElement element = InformationElement.builder()
                    .elementId(id)
                    .length(len)
                    .body(infoBody)
                    .build();

            parsedElements.add(element);
        }

        return parsedElements;
    }
}
