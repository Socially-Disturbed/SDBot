package socially.disturbed.api.pubg;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class ApiClient {
    private static ApiClient apiClient;

    private final String acceptHeader;
    private final String apiKey;

    private final static String baseUrl = "https://api.pubg.com/shards/steam";

    private ApiClient() {
        apiKey = System.getenv("apikey");
        acceptHeader = "application/vnd.api+json";
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public String makeRequest(String endpoint) {
        if (apiKey == null) throw new RuntimeException("ApiKey mangler.");

        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {

            HttpGet httpGet = new HttpGet(baseUrl + endpoint);
            httpGet.setHeader("Authorization", "Bearer " + apiKey);
            httpGet.setHeader("Accept", acceptHeader);

            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
