package analyzer.model.frame;

import lombok.Builder;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Data
@Builder
public class GenericManagementFrame {

    private ManagementFrameSubtype frameControl;
    private short duration;
    private byte[] da;
    private byte[] sa;
    private byte[] bssid;
    private byte[] seqCtrl;
    private FrameBody frameBody;
    private byte[] fcs;

    public static GenericManagementFrame parse(byte[] bytes) {
        return GenericManagementFrame.builder()
                .frameControl(ManagementFrameSubtype.getType(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 2)).getShort()))
                .duration(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 2, 4)).getShort())
                .da(Arrays.copyOfRange(bytes, 4, 10))
                .sa(Arrays.copyOfRange(bytes, 10, 16))
                .bssid(Arrays.copyOfRange(bytes, 16, 22))
                .seqCtrl(Arrays.copyOfRange(bytes, 22, 24))
                .frameBody(FrameBody.parse(Arrays.copyOfRange(bytes, 24, bytes.length - 4)))
                .fcs(Arrays.copyOfRange(bytes, bytes.length - 4, bytes.length))
                .build();
    }
}
