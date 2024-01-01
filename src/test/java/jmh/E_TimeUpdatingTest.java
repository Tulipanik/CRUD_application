package jmh;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class E_TimeUpdatingTest {

    private HttpURLConnection connection;
    private String titleChange = "";

    @Setup(Level.Iteration)
    public void setup () {
        titleChange += "n";
        String noteToAdd = "{\n" +
                "  \"title\": \"" + titleChange +"\",\n" +
                "  \"content\": \"Some text\",\n" +
                "  \"userId\": \"2\"\n" +
                "}";

        try {
            URL url = new URL("http://localhost:8080/notes/1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream os = connection.getOutputStream();
            byte[] input = noteToAdd.getBytes("UTF-8");
            os.write(input, 0, input.length);

        } catch (Exception e){
            throw new RuntimeException("Something's went wrong with setup");
        }
    }

    @Benchmark
    public int testAddingTime () throws IOException {
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        return responseCode;
    }

}

