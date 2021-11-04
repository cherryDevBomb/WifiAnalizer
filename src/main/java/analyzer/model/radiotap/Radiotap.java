package analyzer.model.radiotap;

import analyzer.util.ByteUtils;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
public class Radiotap {

    private RadiotapHeader radiotapHeader;
    private ChannelField channel;
    private AntennaSignalField antennaSignal;

    public static Radiotap parse(byte[] bytes) {
        RadiotapHeader radiotapHeader = RadiotapHeader.parse(Arrays.copyOfRange(bytes, 0, 8));
        ChannelField channelField = null;
        AntennaSignalField antennaSignalField = null;

        // if byte 31 is set in "present" bitmask, other present fields follow
        int offset = 8;
        boolean[] lastPresentBitmask = radiotapHeader.getPresent();
        while (lastPresentBitmask[30]) {
            lastPresentBitmask = ByteUtils.byteArrayToBooleanArray(Arrays.copyOfRange(bytes, offset, offset + 4));
            offset += 4;
        }

        // data follows after the last "present" bitmask
        boolean[] presentBitmask = radiotapHeader.getPresent();
        DefinedField[] definedFields = DefinedField.values();

        for (int i = 0; i < definedFields.length; i++) {
            // handle field alignment
            if (offset % definedFields[i].getAlignment() != 0) {
                offset += (definedFields[i].getAlignment() - (offset % definedFields[i].getAlignment()));
            }

            byte[] fieldBytes = Arrays.copyOfRange(bytes, offset, definedFields[i].getLengthInBytes());

            if (presentBitmask[i]) {
                switch (definedFields[i]) {
                    case CHANNEL:
                        channelField = ChannelField.parse(fieldBytes);
                        break;
                    case ANTENNA_SIGNAL:
                        antennaSignalField = AntennaSignalField.parse(fieldBytes);
                        break;
                    default:
                        break;
                }
            }
        }

        return Radiotap.builder()
                .radiotapHeader(radiotapHeader)
                .channel(channelField)
                .antennaSignal(antennaSignalField)
                .build();
    }
}
