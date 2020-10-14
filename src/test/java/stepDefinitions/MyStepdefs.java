package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MyStepdefs {

    String season = "2017";
    int numberOfRaces = 20;

    @Given("I want to know the number of Formula One races in <season>")
    public void iWantToKnowTheNumberOfFormulaOneRacesInSeason() {

        // First, assert the total number of races in a season
        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(numberOfRaces));

    }

    @When("I retrieve the circuit list for that season")
    public void iRetrieveTheCircuitListForThatSeason() {

//    Validating the correctness of technical response data
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                header("Content-Length",equalTo("4551"));
    }

    @Then("there should be <numberOfCircuits> circuits in the list returned")
    public void thereShouldBeNumberOfCircuitsCircuitsInTheListReturned() {
//Validating  <numberOfCircuits> is returned correctly
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }
}