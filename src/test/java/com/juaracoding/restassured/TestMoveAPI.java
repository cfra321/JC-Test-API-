package com.juaracoding.restassured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestMoveAPI {
    String NowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=695aef390f889becc331665f86859072&language=en-US&page=1";
    String baseMovePopularUrl = "https://api.themoviedb.org/3/movie/popular?api_key=695aef390f889becc331665f86859072&language=en-US&page=3";
    @Test
    public void testNowPlayingUrl() {
        Response  Cek = RestAssured.get(NowPlayingUrl);
        System.out.println(Cek.getBody().asString());
        System.out.println(Cek.getStatusCode());
        System.out.println(Cek.getTime());
        System.out.println(Cek.header("Content-Type"));
        Assert.assertEquals(Cek.getStatusCode(),200);
    }

    @Test
    public void testNowPlayingOne(){
        given().get(NowPlayingUrl)
                .then()
                .statusCode(200)
                .body("results.id[0]",equalTo(505642));

    }
    @Test
    public void testNowPlayingTwo(){
        given().get(NowPlayingUrl)
                .then()
                .statusCode(200)
                .body("results.original_title[0]",equalTo("Black Panther: Wakanda Forever"));
    }

    @Test
    public void testMovePopularUrl() {
        Response Cek = RestAssured.get(baseMovePopularUrl);
        System.out.println(Cek.getBody().asString());
        System.out.println(Cek.getStatusCode());
        System.out.println(Cek.getTime());
        System.out.println(Cek.header("Content-Type"));
        Assert.assertEquals(Cek.getStatusCode(),200);
    }

    @Test
    public void testMovePopularOne(){
        given().get(baseMovePopularUrl)
                .then()
                .statusCode(200)
                .body("results.original_title[0]",equalTo("鬼滅の刃 兄妹の絆"));
    }

    @Test
    public void testMovePopularTwo(){
        given().get(baseMovePopularUrl)
                .then()
                .statusCode(200)
                .body("results.original_title[2]",equalTo("The Offering"));
    }
    @Test
    public void testMoveRate(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("value", "8.5");
        System.out.println(requestBody.toJSONString());

        given()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OTVhZWYzOTBmODg5YmVjYzMzMTY2NWY4Njg1OTA3MiIsInN1YiI6IjY0MDczMTVkYWZlMjI0MDA3YzkyMTkxNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7iZNyKet5Sl-POWM0VUzO9XE6d4JlxTsTPJlAX5cVAM")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody.toJSONString())
                .when()
                .post("https://api.themoviedb.org/3/movie/63184/rating")
                .then()
                .statusCode(201)
                .body("status_message",equalTo("The item/record was updated successfully."))
                .log().all();
    }
}
