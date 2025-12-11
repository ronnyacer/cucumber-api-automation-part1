package com.example.api.steps;

import com.example.api.utils.ResourceUtils;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Reporter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;


import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserSteps {
    private Response response;
    private String requestBody;

    @Given("the API is available")
    public void theAPIIsAvailable() {
    }

//    @When("I send GET request to {string}")
//    public void iSendGETRequestTo(String arg0) {
//        // Write code here that turns the phrase above into concrete actions
////    }

    @Given("I load the input file {string}")
    public void iLoadTheInputFile(String fileName) {
        requestBody = ResourceUtils.readResourceAsString("input/" + fileName);
    }

    @When("I send {string} request to {string}")
    public void iSendRequestTo(String arg0, String arg1) {
        response = given().log().all().body(requestBody)
                .when()
                .post(arg1);
    }


//    @Then("the response status should be {string}")
//    public void theResponseStatusShouldBe(String arg0) {
//        // Write code here that turns the phrase above into concrete actions
////
//    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int arg0) {
        response.then().log().all().statusCode(arg0);
    }

    @Then("the response should match {string} ignoring fields")
    public void theResponseShouldMatchIgnoringFields(String expectedFile, io.cucumber.datatable.DataTable table) {
        // Write code here that turns the phrase above into concrete actions
        List<String> ignoreFields = table.asList();
        String expectedJson= ResourceUtils.readResourceAsString("expected/"+expectedFile);
        JsonPath actual= new JsonPath(response.asString());
        JsonPath expected= new JsonPath(expectedJson);
        Map<String, Object> actualMap= actual.getMap("");
        Map<String,Object>expectedMap = expected.getMap("");

        for (String field: ignoreFields){
            actualMap.remove(field);
            expectedMap.remove(field);
        }
        assertThat(actualMap,equalTo(expectedMap));
    }
}
