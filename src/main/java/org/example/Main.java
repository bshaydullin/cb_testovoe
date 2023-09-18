import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.io.File;
import static io.restassured.RestAssured.given;

class DownloadFileAPITest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://cbr.ru";
    }

    @Test
    @DisplayName("Тест скачивания файла через API")
    public void testDownloadFileViaAPI() {
        Response response = given()
                .when()
                .get("/s/newbik");

        Assertions.assertEquals(200, response.getStatusCode(), "Ошибка: Не удалось выполнить запрос");

        String expectedFileName = "20230918_ED807_full.xml";
        response.then().assertThat().contentType("application/xml"); // Проверяем, что ответ содержит XML
        File downloadedFile = new File(expectedFileName);
        response.getBody().asInputStream().toFile(downloadedFile);
        Assertions.assertTrue(downloadedFile.exists(), "Ошибка: Файл " + expectedFileName + " не был скачан");
        downloadedFile.delete();
    }
}
