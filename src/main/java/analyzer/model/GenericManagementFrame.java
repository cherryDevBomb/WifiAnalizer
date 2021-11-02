package analyzer.model;

import lombok.Builder;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.Line;

@Data
@Builder
public class GenericManagementFrame {

    private ManagementFrameSubtype frameControl;
    private short duration;
    private byte[] da;
    private byte[] sa;
    private byte[] bssid;
    private byte[] seqCtrl;
    private List<InformationElement> informationElements;
    private byte[] fcs;

    public static GenericManagementFrame parse(byte[] bytes) {
        return GenericManagementFrame.builder()
                .frameControl(ManagementFrameSubtype.getType(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort()))
                .duration(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort())
                .da(Arrays.copyOfRange(bytes, 4, 10))
                .sa(Arrays.copyOfRange(bytes, 10, 16))
                .bssid(Arrays.copyOfRange(bytes, 16, 22))
                .seqCtrl(Arrays.copyOfRange(bytes, 22, 24))
                .informationElements(InformationElement.parseList(Arrays.copyOfRange(bytes, 24, bytes.length - 4)))
                .fcs(Arrays.copyOfRange(bytes, bytes.length - 4, bytes.length))
                .build();
    }

    public Optional<InformationElement> getInformationElementById(InformationElementID elementID) {
        return informationElements.stream()
                .filter(element -> elementID.getValue() == element.getElementId())
                .findFirst();
    }
}
