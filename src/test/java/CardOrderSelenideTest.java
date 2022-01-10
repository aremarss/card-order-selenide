import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.*;

public class CardOrderSelenideTest {

    @BeforeAll
    static void setUp() {
        Configuration.headless = false;
    }

    Calendar calendar = new GregorianCalendar();
    String setDate = calendar.get(Calendar.DAY_OF_YEAR) + "." + calendar.get(Calendar.MONTH) + 1 + "." + calendar.get(Calendar.YEAR);

    @Test
    void shouldReturnSuccess() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, ofSeconds(15));
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
    void shouldReturnFailWithEmptyNames() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("");
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailWithIncorrectNames() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Ivan");
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnFailWithEmptyPhone() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldReturnFailWithIncorrectPhone() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("8953");
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldReturnFailWithEmptyCheckbox() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement'] .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}