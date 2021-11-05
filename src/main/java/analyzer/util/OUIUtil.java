package analyzer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OUIUtil {

    private static final String OUI_JSON_FILE_PATH = "analyzer/oui.json";

    private static Map<String, String> ouiMap;

    static {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = OUIUtil.class.getClassLoader().getResourceAsStream(OUI_JSON_FILE_PATH);
        try {
            ouiMap = mapper.readValue(inputStream, new TypeReference<HashMap<String, String>>() {});
        } catch (IOException e) {
            log.error("Error getting map of vendors", e);
        }
    }

    public static String getVendorByOUI(String oui) {
        return ouiMap.get(oui);
    }
}
