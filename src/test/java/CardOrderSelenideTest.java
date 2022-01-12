import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.*;

public class CardOrderSelenideTest {

    @BeforeAll
    static void setUp() {
        Configuration.headless = true;
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Test
    void shouldReturnSuccess() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldReturnFailWithEmptyCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("");
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailWithIncorrectCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Moscow");
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnFailWithEmptyDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldReturnFailWithIncorrectDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(0));
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldReturnFailWithEmptyNames() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("");
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailWithIncorrectNames() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Ivan");
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnFailWithEmptyPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailWithIncorrectPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("8953");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldReturnFailWithEmptyCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}