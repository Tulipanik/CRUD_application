package com.example.backend.updating;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.net.ConnectException;
import java.rmi.UnexpectedException;

import static org.hamcrest.Matchers.containsString;

@Slf4j
public class UpdatingNoteSteps  extends Steps {

    private String apiUrl;
    private String requestBody;
    private Response response;

    private String chosenId;
    private String responseBody;
    private String newResponseBody;
    private Response inputResponse;
    private Response outputResponse;
    private final int StatusOK=201;

    @Given("I have api endpoint \"$apiUrl\"")
    public void givenIHaveApiEndpoint(String apiUrl) {
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
    }

    @Given("I choose note to update:$requestBody")
    public void givenNewNote(String requestBody) {
        this.requestBody = requestBody;
        int[] ddd={requestBody.indexOf("\"userId\": \"")+11,requestBody.indexOf("\"\r\n}")};
        String groupId=requestBody.substring(requestBody.indexOf("\"userId\": \"")+11,requestBody.indexOf("\"\r\n}"));

        log.info("Request: {}", requestBody);
        this.response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(apiUrl);
        log.info("Response: {}", response.asString());

        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/user/"+groupId);
        log.info("Response: {}", inputResponse.asString());
        //Getting id of first note
        var body=inputResponse.body().asString();
        chosenId=body.substring(body.indexOf(":\"")+2,body.indexOf("\","));
    }

    @When("Change data to:$newRequest")
    public void whenChangeDataTo(String newRequest){
        newResponseBody=newRequest;
    }

    @When("I send PUT request with different id than chosen")
    public void whenISendPutRequestWithDifferentIdThanChosen(){
        String wrongId="111111111111111111111111111111111111111111";
        newResponseBody=newResponseBody.substring(0,12)+"\""+wrongId+"\""+newResponseBody.substring(12);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(newResponseBody)
                .put(apiUrl+"/"+chosenId);
        log.info("Response: {}", inputResponse.asString());
    }

    @When("I send PUT request with chosen note id")
    public void whenISendPutRequestWithChosenId(){
        newResponseBody=newResponseBody.substring(0,12)+"\""+chosenId+"\""+newResponseBody.substring(12);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(newResponseBody)
                .put(apiUrl+"/"+chosenId);
        log.info("Response: {}", inputResponse.asString());
    }

    @Then("Chosen note should contains new data")
    public void thenChosenNoteShouldContainsNewData() throws UnexpectedException {
        this.outputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/"+chosenId);
        log.info("Response: {}", outputResponse.asString());
        if(!outputResponse.body().toString().equals(newResponseBody))
        {
            throw new UnexpectedException("Status code is different that expected! Status Code: "+outputResponse.statusCode());
        }
    }

    @Then("Chosen note should be same")
    public void thenChosenNoteShouldBeSame() throws UnexpectedException {
        this.requestBody=requestBody.substring(0,4)+"  \"id\": \""+chosenId+"\","+requestBody.substring(4);
        this.outputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/"+chosenId);
        log.info("Response: {}", outputResponse.asString());
        if(!outputResponse.body().toString().equals(requestBody))
        {
            throw new UnexpectedException("Note change but shouldn't!");
        }
    }

    @Then("It should return status code\"$statusCode\"")
    public void thenChosenNoteShouldContainsNewData(int statusCode) throws UnexpectedException {

        if(outputResponse.statusCode()!=statusCode)
        {
            throw new UnexpectedException("Status code is different that expected! Status Code: "+outputResponse.statusCode());
        }
    }
}
