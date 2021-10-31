package analyzer.model;

import javafx.beans.property.SimpleStringProperty;

public class WirelessNetworkInfo {

    private final SimpleStringProperty SSID = new SimpleStringProperty("");
    private final SimpleStringProperty MAC = new SimpleStringProperty("");

    public WirelessNetworkInfo() {
        setSSID("ssid");
        setMAC("mac");
    }

    public WirelessNetworkInfo(String SSID, String MAC) {
        setSSID(SSID);
        setMAC(MAC);
    }

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
}
