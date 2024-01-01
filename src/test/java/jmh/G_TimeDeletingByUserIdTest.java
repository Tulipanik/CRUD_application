package jmh;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class G_TimeDeletingByUserIdTest {
    private HttpURLConnection connection;
    private int userId = 15;
    @Setup
    public void setup () {
        userId++;
        try {
            URL url = new URL("http://localhost:8080/notes/user/" + userId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");

        } catch (Exception e){
            throw new RuntimeException("Something's went wrong with setup");
        }
    }

    @Benchmark
    public int test () throws IOException {
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        return responseCode;
    }

}
