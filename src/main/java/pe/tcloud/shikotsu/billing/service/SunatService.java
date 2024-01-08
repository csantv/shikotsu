package pe.tcloud.shikotsu.billing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pe.tcloud.shikotsu.config.SunatConfiguration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class SunatService {
    private final URI invoicePdfUri;

    public SunatService(SunatConfiguration configuration) {
        var builder = UriComponentsBuilder.fromHttpUrl(configuration.baseUrl());
        builder.pathSegment("api", "v1", "invoice", "pdf");
        builder.queryParam("token", List.of(configuration.token()));
        this.invoicePdfUri = builder.build().toUri();
    }

    public void testRequest() {
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(invoicePdfUri)
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            System.out.println("Got IOException");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
