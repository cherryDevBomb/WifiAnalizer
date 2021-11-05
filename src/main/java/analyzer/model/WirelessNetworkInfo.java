package analyzer.model;

import javafx.beans.property.SimpleStringProperty;

public class WirelessNetworkInfo {

    private final SimpleStringProperty SSID = new SimpleStringProperty("");
    private final SimpleStringProperty MAC = new SimpleStringProperty("");
    private final SimpleStringProperty vendor = new SimpleStringProperty("");
    private final SimpleStringProperty signalPower = new SimpleStringProperty("");
    private final SimpleStringProperty channel = new SimpleStringProperty("");
    private final SimpleStringProperty frequency = new SimpleStringProperty("");

    public String getSSID() {
        return SSID.get();
    }

    public SimpleStringProperty SSIDProperty() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID.set(SSID);
    }

    public String getMAC() {
        return MAC.get();
    }

    public SimpleStringProperty MACProperty() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC.set(MAC);
    }

    public String getVendor() {
        return vendor.get();
    }

    public SimpleStringProperty vendorProperty() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor.set(vendor);
    }

    public String getSignalPower() {
        return signalPower.get();
    }

    public SimpleStringProperty signalPowerProperty() {
        return signalPower;
    }

    public void setSignalPower(String signalPower) {
        this.signalPower.set(signalPower);
    }

    public String getChannel() {
        return channel.get();
    }

    public SimpleStringProperty channelProperty() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel.set(channel);
    }

    public String getFrequency() {
        return frequency.get();
    }

    public SimpleStringProperty frequencyProperty() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency.set(frequency);
    }
}
