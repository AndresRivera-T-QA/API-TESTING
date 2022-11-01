import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ejemplo_test_api {
    @Test
    public void api_test(){
        //URL y URI
        RestAssured.baseURI = String.format("https://reqres.in/api/users?page=2");

        //Response response = given().headers("Accept","*/*").get();

        // Muestra un response más detallado
        Response response = given().
                log().all().
                headers("Accept","*/*").get();

        // Realiza e imprime la llamada bajo un formato estético del body
        String body_response = response.getBody().prettyPrint();
        System.out.println("Body" + body_response);

        // Imprime el status
        String responseCode = String.format(String.valueOf(response.getStatusCode()));
        System.out.println("Status code" + responseCode);

        //Librería de JUnit 5 para test (Aserción)

        // Valida el Nº del status
        assertEquals(200,response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga la palabra Id
        assertTrue(body_response.contains("id"));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 800);

        // Valida los header
        String header_response = response.getHeaders().toString();
        System.out.println("Los Headers son:" + header_response);

        assertTrue(header_response.contains("application/json"));


    }

    @Test
    public void add_pet_test(){
        RestAssured.baseURI = String.format("https://petstore.swagger.io/v2/pet");

        String bodyRequest = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"Perro de raza chica\"\n" +
                "  },\n" +
                "  \"name\": \"Solovino\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"manchado\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = given()
                .log().all()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .post(bodyRequest);

        String body_response = response.getBody().prettyPrint();
        System.out.println("Body response: " + body_response);
        System.out.println("Código de respuesta: " + response.getStatusCode());
    }
}

