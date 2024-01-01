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
public class D_TimeGettingAllTest {

    private HttpURLConnection connection;
    @Setup
    public void setup () {
        try {
            URL url = new URL("http://localhost:8080/notes");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

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

