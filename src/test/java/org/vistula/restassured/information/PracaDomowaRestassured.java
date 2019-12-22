package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PracaDomowaRestassured extends RestAssuredTest {

    @Test
    public void shouldCorrectlyUpdateAllData (){
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "England");
        requestParams.put("salary", 997);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("name", "Wojtek");
        newPlayer.put("nationality", "Netherland");
        newPlayer.put("salary", 112);

        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .put("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Wojtek"))
                .body("nationality", equalTo("Netherland"))
                .body("salary", equalTo(112));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldCorrectlyUpdateName () {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "England");
        requestParams.put("salary", 997);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("name", "Gracz1");
        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .patch("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Gracz1"));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldCorrectlyUpdateSalary () {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "England");
        requestParams.put("salary", 997);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("salary", 112);
        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .patch("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", equalTo(112));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

}
