import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SimpleWeather {
    public static void main(String[] args) throws MalformedURLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simple Weather Program");
        String base_url = "https://api.openweathermap.org/data/2.5/weather";
        System.out.println("Enter your API key");
        String apiInput = scanner.nextLine();

        System.out.println("Enter a city");
        String cityInput = scanner.nextLine().toLowerCase();

        String complete_url = base_url +"?q=" + cityInput + "&units=imperial&APPID=" + apiInput;
        System.out.println("sending request to " + complete_url);

        HttpClient client = HttpClient.newHttpClient();

        // Create an HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(complete_url))
                .GET() // Specify that this is a GET request
                .build();

        // Send the request and receive a response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println) // Print the response body
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });

        client.close();
        scanner.close();

    }
}
