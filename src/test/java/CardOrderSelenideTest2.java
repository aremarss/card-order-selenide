import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;

public class CardOrderSelenideTest2 {

    @BeforeAll
    static void setUp() {
        Configuration.headless = true;
    }

    Calendar calendar = new GregorianCalendar();
    String setDate = calendar.get(Calendar.DAY_OF_YEAR) + "." + calendar.get(Calendar.MONTH) + 1 + "." + calendar.get(Calendar.YEAR);

    @Test
    void shouldChooseCityFromList() {
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ка");
        $(".popup .menu").find(withText("Казань")).shouldHave(visible).click();
        $("[data-test-id='date'] input").setValue(setDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, ofSeconds(15));
    }

    @Test
    void shouldChooseCityAndDateFromList() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ка");
        $(".popup .menu").find(withText("Казань")).shouldHave(visible).click();
        $("[data-test-id='date'] .input__icon .icon-button").click();
        int currentDate = Integer.parseInt($(".popup_visible .calendar__layout .calendar__day_state_current").getText());
        $(".popup_visible .calendar__layout").find(byText(String.valueOf(currentDate + 7))).click();
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, ofSeconds(15));
    }
}
