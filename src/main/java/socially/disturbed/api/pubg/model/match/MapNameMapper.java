package socially.disturbed.api.pubg.model.match;

import java.util.Map;

public class MapNameMapper {

    public static Map<String, String> apiToCommonMap = Map.of(
            ApiMapName.BALTIC_MAIN.value, CommonMapName.ERANGEL.value,
            ApiMapName.TIGER_MAIN.value, CommonMapName.TAEGO.value,
            ApiMapName.DESERT_MAIN.value, CommonMapName.MIRAMAR.value
    );

    private enum ApiMapName {
        BALTIC_MAIN("Baltic_Main"),
        TIGER_MAIN("Tiger_Main"),
        DESERT_MAIN("Desert_Main");

        private final String value;

        ApiMapName(String value) {
            this.value = value;
        }
    }

    private enum CommonMapName {
        ERANGEL("Erangel"),
        TAEGO("Taego"),
        MIRAMAR("Miramar");

        private final String value;

        CommonMapName(String value) {
            this.value = value;
        }
    }
}