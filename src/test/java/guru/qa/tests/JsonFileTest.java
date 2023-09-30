package guru.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.JsonUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JsonFileTest {
    private String pathToJson = "src/test/resources/files/User.json";

    @Test
    @DisplayName("Проверка содержимого JSON-файла")
    void checkingTheJsonContents() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonUser user = objectMapper.readValue(new File(pathToJson), JsonUser.class);
        Assertions.assertEquals("7", user.getId());
        Assertions.assertEquals("michael.lawson@reqres.in", user.getEmail());
        Assertions.assertEquals("Michael", user.getFirstName());
        Assertions.assertEquals("Lawson", user.getLastName());
        Assertions.assertEquals("https://reqres.in/img/faces/7-image.jpg", user.getAvatar());
    }

}
