package com.example.backend.updating;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.StringContains;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.rmi.UnexpectedException;

import static org.hamcrest.Matchers.containsString;

@Slf4j
public class UpdatingNoteSteps  extends Steps {

    private String apiUrl;
    private String requestBody;

    private String chosenId;
    private String newRequestBody;
    private Response inputResponse;
    private Response outputResponse;

    @Given("I have api endpoint \"$apiUrl\"")
    public void givenIHaveApiEndpoint(String apiUrl) {
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
    }

    @Given("I choose note to update:$requestBody")
    public void givenNewNote(String requestBody) {
        this.requestBody = requestBody;
        //Getting user id
        String groupId=requestBody.substring(requestBody.indexOf("\"userId\": \"")+11,requestBody.indexOf("\"\r\n}"));

        log.info("Request: {}", requestBody);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(apiUrl);
        log.info("Response: {}", inputResponse.asString());

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
        newRequestBody =newRequest;
    }

    @When("I send PUT request with different id than chosen")
    public void whenISendPutRequestWithDifferentIdThanChosen(){
        String wrongId="111111111111111111111111111111111111111111";
        //Adding id
        newRequestBody = newRequestBody.substring(0,12)+"\""+wrongId+"\""+ newRequestBody.substring(12);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(newRequestBody)
                .put(apiUrl+"/"+chosenId);
        log.info("Response: {}", inputResponse.asString());
    }

    @When("I send PUT request with chosen note id")
    public void whenISendPutRequestWithChosenId(){
        //Adding id
        newRequestBody = newRequestBody.substring(0,12)+"\""+chosenId+"\""+ newRequestBody.substring(12);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(newRequestBody)
                .put(apiUrl+"/"+chosenId);
        log.info("Response: {}", inputResponse);
    }

    @Then("Chosen note should contains new data")
    public void thenChosenNoteShouldContainsNewData() throws UnexpectedException {
        this.outputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/"+chosenId);
        log.info("Response: {}", outputResponse.asString());
        //Dodaję wartości na sztywno, bo inaczej nie działa
        outputResponse.then()
                .body(containsString("\"title\":\"My corrected note\""))
                .body(containsString("\"content\":\"This is my corrected note\""))
                .body(containsString("\"userId\":\"123456\""));
    }

    @Then("Chosen note should be same")
    public void thenChosenNoteShouldBeSame() throws UnexpectedException {
        String ddd=apiUrl+"/"+chosenId;
        this.outputResponse = RestAssured.given()
                .contentType("application/json")
                .get(ddd);
        log.info("Response: {}", outputResponse);
        //Dodaję wartości na sztywno, bo inaczej nie działa
        outputResponse.then()
                .body(containsString("\"title\":\"My new note\""))
                .body(containsString("\"content\":\"This is my new note\""))
                .body(containsString("\"userId\":\"12345678\""));

    }

    @Then("It should return status code \"$statusCode\"")
    public void thenChosenNoteShouldContainsNewData(int statusCode) throws UnexpectedException {

        if(inputResponse.statusCode()!=statusCode)
        {
            throw new UnexpectedException("Status code is different that expected! Status Code: "+inputResponse.statusCode());
        }
    }
}
