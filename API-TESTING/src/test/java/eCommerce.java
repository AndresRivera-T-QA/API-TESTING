import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Name;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class eCommerce {

    // Variables
    static private String url_base = "https://webapi.segundamano.mx";
    static private String email = "test2022_pruebas2@mailinator.com";
    static private String password = "api123";
    static private String access_token;
    static private String account_id;
    static private String uuid;
    static private String ad_id;
    public static String getAd_id() {
        return ad_id;
    }
    static private String addressID;

    public static String getAddressID() {
        return addressID;
    }

    @Name("Obtener Token")
    private String obtener_Token() {
        RestAssured.baseURI = String.format("%s/nga/api/v1.1/private/accounts?lang=es", url_base);

        Response response = given()
                .log().all()
                .queryParam("lang", "es")
                .auth().preemptive().basic(email, password)
                .post();

        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Cambiar el body a Json
        JsonPath jsonResponse = response.jsonPath();

        String accesstoken = jsonResponse.get("access_token");
        System.out.println("Token en funcion: " + accesstoken);
        access_token = accesstoken;

        // Otras variables
        String accountID = jsonResponse.get("account.account_id");
        System.out.println("account id en funcion: " + accountID);
        account_id = accountID;

        // Asignar la variable uuid
        String uid = jsonResponse.get("account.uuid");
        System.out.println("uuid en funcion: " + uid);
        uuid = uid;

        String ad = jsonResponse.get("data.ad.ad_id");
        System.out.println("ad_id en funcion: " + ad);
        ad_id = ad;

        String address = jsonResponse.get("addressID");
        System.out.println("ad_id en funcion: " + address);
        addressID = address;


        return access_token;

    }

    @Test
    @Order(1)
    @DisplayName("Test case 1: Obtener categorias")
    @Severity(SeverityLevel.BLOCKER)
    public void get_obtener_categorias_200() {
        //RestAssured.baseURI = String.format("{{url_base}}/nga/api/v1.1/public/categories/filter?lang=es",url_base);
        RestAssured.baseURI = String.format("%s/nga/api/v1.1/public/categories/filter?lang=es", url_base);

        Response response = given()
                .log().all()
                .queryParam("lang", "es")
                .filter(new AllureRestAssured())
                .get();

        // Imprimir el body
        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(200, response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga la palabra Id
        assertTrue(body_response.contains("id"));
        assertTrue(body_response.contains("categories"));
        assertTrue(body_response.contains("all_label"));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 900);

        // Valida los header
        String header_response = response.getHeaders().toString();
        System.out.println("Los Headers son:" + header_response);

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(2)
    @DisplayName("Test case 2: Listado de Productos")
    @Severity(SeverityLevel.BLOCKER)
    public void post_listado_productos_200() {

        String bodyRequest = "{\"filters\":[{\"price\":\"-60000\",\"category\":\"2020\"},{\"price\":\"60000-80000\",\"category\":\"2020\"},{\"price\":\"80000-100000\",\"category\":\"2020\"},{\"price\":\"100000-150000\",\"category\":\"2020\"},{\"price\":\"150000-\",\"category\":\"2020\"}]}";

        //RestAssured.baseURI = String.format("{{url_base}}/urls/v1/public/ad-listing?lang=es",url_base);
        RestAssured.baseURI = String.format("%s/urls/v1/public/ad-listing?lang=es", url_base);

        Response response = given()
                .log().all()
                .queryParam("lang", "es")
                .contentType("application/json")
                .auth().preemptive().basic(email, password)
                .body(bodyRequest)
                .filter(new AllureRestAssured())
                .post();

        // Imprimir el body
        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(200, response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga la palabra Id
        assertTrue(body_response.contains("data"));


        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3000);

        // Valida los header
        String header_response = response.getHeaders().toString();
        System.out.println("Los Headers son:" + header_response);

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(3)
    @DisplayName("Test case 3: Selección Iphone")
    @Severity(SeverityLevel.BLOCKER)
    public void get_seleccion_iphone_200() {
        //RestAssured.baseURI = String.format("{{url_base}}/nga/api/v1/public/messaging/adinfo?ids=939126427&ts=1667194841366",url_base);
        RestAssured.baseURI = String.format("%s/nga/api/v1/public/messaging/adinfo?ids=939126427&ts=1667194841366", url_base);

        Response response = given()
                .log().all()
                .queryParam("ids", "939126427")
                .queryParam("ts", "1667194841366")
                .filter(new AllureRestAssured())
                .get();

        // Imprimir el body
        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(200, response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga la palabra Id
        assertTrue(body_response.contains("status"));
        assertTrue(body_response.contains("ads"));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3000);

        // Valida los header
        String header_response = response.getHeaders().toString();
        System.out.println("Los Headers son:" + header_response);

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(4)
    @DisplayName("Test case 4: Crear usuario")
    @Severity(SeverityLevel.BLOCKER)
    public void post_crearUsuario_401() {

        //Crear Usuario
        String new_user = "agente_ventas" + (Math.floor(Math.random() * 987)) + "@mailinator.com";
        // String password = "12345"; // Para evitar conflicto con el environment global
        //String bodyRequest = "{\"account\":{\"email\":\"{{correo}}\"}}";
        String bodyRequest = "{\"account\":{\"email\":\"" + new_user + "\"}}";

        RestAssured.baseURI = String.format("%s/nga/api/v1.1/private/accounts?lang=es", url_base);

        Response response = given()
                .log().all()
                .queryParam("lang", "es")
                .contentType("application/json")
                .auth().preemptive().basic(new_user, password)
                .body(bodyRequest)
                .filter(new AllureRestAssured())
                .post();

        // Imprimir el body
        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(401, response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga cierta palabras del body
        assertTrue(body_response.contains("error"));
        assertTrue(body_response.contains("code"));
        assertTrue(body_response.contains("ACCOUNT_VERIFICATION_REQUIRED"));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 1800);

        // Valida los header
        String header_response = response.getHeaders().toString();
        System.out.println("Los Headers son:" + header_response);

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(5)
    @DisplayName("Test case 5: Ingresar usuario")
    @Severity(SeverityLevel.BLOCKER)
    public void post_IngresarUsuario_200() {

        // Configuración
        String bodyRequest = "{\"account\":{\"email\":\"" + email + "\"}}";

        // Ejecución
        RestAssured.baseURI = String.format("%s/nga/api/v1.1/private/accounts?lang=es", url_base);

        Response response = given()
                .log().all()
                .queryParam("lang", "es")
                .contentType("application/json")
                .auth().preemptive().basic(email, password)
                .body(bodyRequest)
                .post();

        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(200, response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga cierta palabras del body
        assertTrue(body_response.contains("access_token"));
        assertTrue(body_response.contains("account_id"));
        assertTrue(body_response.contains("test2022_pruebas2@mailinator.com"));
        assertTrue(body_response.contains(email));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 6000);

        // Valida los header
        String header_response = response.getHeaders().toString();

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(6)
    @DisplayName("Test case 6: Editar usuario")
    @Severity(SeverityLevel.BLOCKER)
    public void patch_editar_datos_usuario_200(){

        String bodyRequest = "{\n" +
                "\t\"account\": {\n" +
                "\t\t\"name\": \"{{nombre_usuario}}\",\n" +
                "\t\t\"phone\": \"{{telefono}}\",\n" +
                "\t\t\"locations\": [{\n" +
                "\t\t\t\"code\": \"4\",\n" +
                "\t\t\t\"key\": \"region\",\n" +
                "\t\t\t\"label\": \"Baja California\",\n" +
                "\t\t\t\"locations\": [{\n" +
                "\t\t\t\t\"code\": \"46\",\n" +
                "\t\t\t\t\"key\": \"municipality\",\n" +
                "\t\t\t\t\"label\": \"Ensenada\"\n" +
                "\t\t\t}]\n" +
                "\t\t}],\n" +
                "\t\t\"professional\": false,\n" +
                "\t\t\"phone_hidden\": false\n" +
                "\t}\n" +
                "}";

        //https://webapi.segundamano.mx/nga/api/v1/private/accounts/12638970?lang=es
        RestAssured.baseURI = String.format("%s/nga/api/v1%s?lang=es",url_base,account_id);

        Response response = given()
                .log().all()
                .queryParam("lang","es")
                .contentType("application/json")
                .auth().preemptive().basic(email,password)
                .body(bodyRequest)
                .filter(new AllureRestAssured())
                .patch();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);


        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 6000);

        // Valida los header
        String header_response = response.getHeaders().toString();

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(7)
    @DisplayName("Test case 7: Consultar usuario")
    @Severity(SeverityLevel.BLOCKER)
    public void get_consultar_usuario_200(){

        //https://webapi.segundamano.mx/nga/api/v1/private/accounts/12638970?lang=es
        RestAssured.baseURI = String.format("%s/nga/api/v1/%s?lang=es",url_base,account_id);

        Response response = given()
                .log().all()
                .queryParam("lang","es")
                .contentType("application/json")
                .filter(new AllureRestAssured())
                .get();

        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 6000);

        // Valida los header
        String header_response = response.getHeaders().toString();

        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(8)
    @DisplayName("Test case 8: Crear Anuncio")
    @Severity(SeverityLevel.BLOCKER)
    public void post_crearAnuncio_200(){

        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);

        String body_request = "{\n" +
                "    \"category\": \"8143\",\n" +
                "    \"subject\": \"Te organizo tu evento\",\n" +
                "    \"body\": \"Trabajamos todo tipo de eventos, desde shower hasta party\",\n" +
                "    \"region\": \"4\",\n" +
                "    \"municipality\": \"46\",\n" +
                "    \"area\": \"35466\",\n" +
                "    \"price\": \"20000\",\n" +
                "    \"phone_hidden\": \"true\",\n" +
                "    \"show_phone\": \"false\",\n" +
                "    \"contact_phone\": \"6289451113\"\n" +
                "}";

        // Ejecución
        //https://webapi.segundamano.mx/v2/accounts/00855d8d-1b92-45b9-a3bf-5c51dcc69afd/up
        RestAssured.baseURI = String.format("%s/v2/accounts/%s/up",url_base,uuid);

        Response response = given()
                .log().all()
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .header("x-source","PHOENIX_DESKTOP")
                .auth().preemptive().basic(uuid,access_token)
                .body(body_request)
                .post();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        // Valida el Nº del status
        assertEquals(200,response.getStatusCode());

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar que el body contenga la palabra ID
        assertTrue(body_response.contains("ad_id"));


        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

        //Validar los headers
        String headers_response = response.getHeaders().toString();
        assertTrue(headers_response.contains("application/json"));

    }

    @Test
    @Order(9)
    @DisplayName("Test Case 9: Editar un Anuncio")
    @Severity(SeverityLevel.BLOCKER)
    public void put_editar_Anuncio_200(){

        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);
        System.out.println("ad id: " + ad_id);

        String body_Request = "{\n" +
                "    \"category\": \"8143\",\n" +
                "    \"subject\": \"Organizamos todo tipo de eventos\",\n" +
                "    \"body\": \"Trabajamos todo tipo de eventos, desde shower hasta party\",\n" +
                "    \"region\": \"4\",\n" +
                "    \"municipality\": \"46\",\n" +
                "    \"area\": \"35466\",\n" +
                "    \"price\": \"20000\",\n" +
                "    \"phone_hidden\": \"true\",\n" +
                "    \"show_phone\": \"false\",\n" +
                "    \"contact_phone\": \"6289451113\"\n" +
                "}";
        //https://webapi.segundamano.mx/v2/accounts/00855d8d-1b92-45b9-a3bf-5c51dcc69afd/up/74880650
        RestAssured.baseURI = String.format("%s/v2/accounts/%s/up/%s",url_base,uuid,ad_id);

        Response response = given()
                .log().all()
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .header("x-source","PHOENIX_DESKTOP")
                .auth().preemptive().basic(uuid,access_token)
                .body(body_Request)
                .put();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

        //Validar los headers
        String headers_response = response.getHeaders().toString();
        assertTrue(headers_response.contains("application/json"));
    }

    @Test
    @Order(10)
    @DisplayName("Test case 10 : Eliminar Anuncio")
    @Severity(SeverityLevel.BLOCKER)
    public void delete_Borrar_Anuncio_200(){
        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);
        System.out.println("ad id: " + ad_id);

        String body_Request = "{\"delete_reason\":{\"code\":\"0\"}}";

        //https://webapi.segundamano.mx/nga/api/v1/private/accounts/12638970/klfst/74884315
        RestAssured.baseURI = String.format("%s/nga/api/v1%s/klfst/%s",url_base,uuid,ad_id);

        Response response = given()
                .log().all()
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .header("Authorization","tag:scmcoord.com,2013:api {{access_token}}")
                .body(body_Request)
                .delete();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

        //Validar los headers
        String headers_response = response.getHeaders().toString();
        assertTrue(headers_response.contains("application/json"));
    }

    @Test
    @Order(11)
    @DisplayName("Test case 11: Crear Dirección")
    @Severity(SeverityLevel.BLOCKER)
    public void post_crear_Direccion_201(){

        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);

        // Ejecución
        //https://webapi.segundamano.mx/addresses/v1/create
        RestAssured.baseURI = String.format("%s/addresses/v1/create",url_base);

        Response response = given()
                .log().all()
                .formParam("contact","Agente de ventas")
                .formParam("phone","8776655443")
                .formParam("rfc","CAPL800101")
                .formParam("zipCode","45999")
                .formParam("exteriorInfo","Miguel Hidalgo 4232")
                .formParam("interiorInfo","2")
                .formParam("region","11")
                .formParam("municipality","300")
                .formParam("area","8094")
                .formParam("alias","La oficina")
                .header("Content-type","application/x-www-form-urlencoded")
                .header("Accept","application/json, text/plain, */*")
                .auth().preemptive().basic(uuid,access_token)
                .post();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        // Valida el Nº del status
        assertEquals(201,response.getStatusCode());

        // Valida que el body no se encuentre vacío
        assertNotNull(body_response);

        // Valida que el body contenga cierta palabras del body
        assertTrue(body_response.contains("addressID"));

        // Valida el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 1800);

        // Valida los header
        String header_response = response.getHeaders().toString();
        assertTrue(header_response.contains("application/json"));

    }

    @Test
    @Order(12)
    @DisplayName("Test case 12: Eliminar Dirección")
    @Severity(SeverityLevel.BLOCKER)
    public void delete_eliminar_Direccion_200(){

        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);

        //https://webapi.segundamano.mx/addresses/v1/delete/1ad558b0-5a31-11ed-8f54-f716458c550b
        RestAssured.baseURI = String.format("%s/v2/accounts/%s/up",url_base,uuid);

        Response response = given()
                .log().all()
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .auth().preemptive().basic(uuid,access_token)
                .delete();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar que el body contenga la palabra ID
        assertTrue(body_response.contains("error"));


        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

        //Validar los headers
        String headers_response = response.getHeaders().toString();
        assertTrue(headers_response.contains("application/json"));

    }

    @Test
    @Order(13)
    @DisplayName("Test case 13: Ver Cartera")
    @Severity(SeverityLevel.BLOCKER)
    public void get_ver_Cartera_200(){

        // https://webapi.segundamano.mx/credits/v1/private/accounts/74884315
        //RestAssured.baseURI = String.format("{{url_base}}/credits/v1/private/accounts/{{ad_id}}",url_base,ad_id);
        RestAssured.baseURI = String.format("%s/credits/v1/private/accounts/74884315",url_base);

        Response response = given()
                .log().all()
                .header("Accept","application/json, text/plain, */*")
                .filter(new AllureRestAssured())
                .get();

        // Imprimir el body
        String body_response = response.getBody().asString();
        System.out.println("Body response" + body_response);

        // Test

        // Valida el Nº del status
        assertEquals(200,response.getStatusCode());

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar que el body contenga la palabra ID
        assertTrue(body_response.contains("balance"));


        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

        //Validar los headers
        String headers_response = response.getHeaders().toString();
        assertTrue(headers_response.contains("application/json"));

    }

    @Test
    @Order(14)
    @DisplayName("Test case 14: Comprar Monedas")
    @Severity(SeverityLevel.BLOCKER)
    public void post_comprar_Monedas_200(){
        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);

        String body_request = "{\n" +
                "    \"products\": [\n" +
                "        {\n" +
                "            \"target_id\": 12638970,\n" +
                "            \"product_id\": \"serv_points_1\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //https://webapi.segundamano.mx/premium/v1/private/account/00855d8d-1b92-45b9-a3bf-5c51dcc69afd/order?lang=es
        RestAssured.baseURI = String.format("%s/premium/v1/private/accounts/%s/order?lang=es",url_base,uuid);

        Response response = given()
                .log().all()
                .queryParam("lang","es")
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .auth().preemptive().basic(uuid,access_token)
                .body(body_request)
                .post();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);

        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);

    }

    @Test
    @Order(15)
    @DisplayName("Test case 15: Generar Código Pago")
    @Severity(SeverityLevel.BLOCKER)
    public void put_generar_código_pago_200(){
        String token = obtener_Token();
        System.out.println("Token: " + token);

        System.out.println("Token de funcion: " + access_token);
        System.out.println("account id de funcion: " + account_id);
        System.out.println("uuid de funcion: " + uuid);

        String body_request = "{\n" +
                "    \"payment_method\": \"oxxo\",\n" +
                "    \"provider_id\": \"premium\",\n" +
                "    \"rfc\": \"\"\n" +
                "}";

        //https://webapi.segundamano.mx/premium/v1/private/account/00855d8d-1b92-45b9-a3bf-5c51dcc69afd/order/27d9341e-be9a-4741-b4d2-5ca93de4af6b?lang=es
        RestAssured.baseURI = String.format("%s/premium/v1/private/accounts/%s/order/27d9341e-be9a-4741-b4d2-5ca93de4af6b?lang=es",url_base,uuid);

        Response response = given()
                .log().all()
                .queryParam("lang","es")
                .queryParam("Key","Value")
                .header("Content-type","application/json")
                .header("Accept","application/json, text/plain, */*")
                .auth().preemptive().basic(uuid,access_token)
                .body(body_request)
                .put();

        String body_response = response.getBody().asString();
        System.out.println("Body response: " + body_response );

        // Test

        //Validar que nuestro body no este vacio
        assertNotNull(body_response);
        
        //Validar el tiempo de respuesta
        long tiempo = response.getTime();
        assertTrue(tiempo < 3800);




    }


}
