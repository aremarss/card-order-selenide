import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
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

    int days = 7; // На сколько дней вперед нужно сделать заказ.

    String planningDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    Month planningMonth(int days) {
        return LocalDate.now().plusDays(days).getMonth();
    }

    String planningDay(int days) {
        return String.valueOf(LocalDate.now().plusDays(days).getDayOfMonth());
    }

    @Test
    void shouldChooseCityFromList() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ка");
        $(".popup .menu").find(withText("Казань")).shouldHave(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate(days));
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planningDate(days)));
    }

    @Test
    void shouldChooseCityAndDateFromList() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ка");
        $(".popup .menu").find(withText("Казань")).shouldHave(visible).click();
        $("[data-test-id='date'] .input__icon .icon-button").click();
        for (int i = 0; i < 12; i++) {
            if (planningMonth(days) != LocalDate.now().plusMonths(i).getMonth()) {
                $("[data-step=\"1\"]").click();
            } else if (planningMonth(days) == LocalDate.now().plusMonths(i).getMonth()) {
                $(".popup_visible .calendar__layout").find(withText(planningDay(days))).click();
                break;
            }
        }
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79003332211");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + planningDate(days)));
    }
}