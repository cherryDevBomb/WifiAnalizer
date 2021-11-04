package analyzer.model.radiotap;

import lombok.Getter;

@Getter
public enum DefinedField {

    TSFT(0, 8, 8),
    FLAGS(1, 1, 1),
    RATE(2, 1, 1),
    CHANNEL(3, 4, 2),
    FHSS(4, 2, 2),
    ANTENNA_SIGNAL(5, 1, 1),
    ANTENNA_NOISE(6, 1, 1),
    LOCK_QUALITY(7, 2, 2),
    TX_ATTENUATION(8, 2, 2),
    DB_TX_ATTENUATION(9, 2, 2),
    DBM_TX_ATTENUATION(10, 1, 1),
    ANTENNA(11, 1, 1),
    DB_SIGNAL(12, 1, 1),
    DB_NOISE(13, 1, 1),
    RX_FLAGS(14, 2, 2),
    TX_FLAGS(15, 2, 2),
    RTS_RETRIES(16, 1, 1),
    DATA_RETRIES(17, 1, 1),
    CHANNEL_PLUS(18, 8, 4);

    private final int bitNumber;
    private final int lengthInBytes;
    private final int alignment;

    DefinedField(int bitNumber, int lengthInBytes, int alignment) {
        this.bitNumber = bitNumber;
        this.lengthInBytes = lengthInBytes;
        this.alignment = alignment;
    }
}
