package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

import static java.lang.System.getProperty;

public class RestClient {

    private static HttpClient client;
    public static String token;
    private static final Logger LOGGER = Logger.getLogger("Steps");

    static {
        long timeout = Long.parseLong(getProperty("restClientTimeout"));
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeout))
                .build();
        token = login().get("access_token").asText();
        LOGGER.info("Token: " + token);
    }

    public static JsonNode ejecutar(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            int statusCode = response.statusCode();

            if (statusCode == 403) {
                String forbiddenMsg = "ERROR 403: El usuario '" + getProperty("username") +
                        "' NO tiene permisos para este servicio.\n" +
                        "Servicio: " + request.method() + " " + request.uri() + "\n" +
                        "Respuesta del servidor: " + body;

                Allure.addAttachment("Causa del 403 (Permisos)", body);
                LOGGER.severe(forbiddenMsg);
                throw new AssertionFailedError(forbiddenMsg);
            }

            if (body.trim().startsWith("<")) {
                Allure.addAttachment("Respuesta no esperada (XML/HTML)", body);
                throw new AssertionFailedError("Se esperaba un JSON pero se recibió un formato diferente (Status: " + statusCode + ")");
            }

            JsonNode json = new ObjectMapper().readTree(response.body());

            String texto =

                    "Request: " + request.method() + " " + request.uri() + "\n" +
                            "Response Code: " + response.statusCode() + "\n" +
                            "Response Body: " + json.toPrettyString();

            if ( ! servicioDeLogin(request.uri().toString()) ) {
                Allure.addAttachment("Response", texto);
            }
            if (statusCode >= 300) {
                throw new AssertionFailedError("Error de servicio (" + statusCode + ")\n" +
                        request.method() + " " + request.uri());
            }
            return json;
        } catch (IOException | InterruptedException e) { throw new RuntimeException(e); }
    }

    private static boolean servicioDeLogin(String uri) {
        return uri.contains(getProperty("restClientUrlAuth") + "/oauth/token?");
    }

    private static JsonNode login() {
        String body = "";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getProperty("restClientUrlAuth") + "/oauth/token?username=" + getProperty("username") + "&password=" + getProperty("password")+ "&grant_type=password"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic V01TLUNsaWVudElkOnM2djl5JEImRSlIQE1jUWVUaFdtWnE0dDd3IXolQypG")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return ejecutar(request);
    }

}
