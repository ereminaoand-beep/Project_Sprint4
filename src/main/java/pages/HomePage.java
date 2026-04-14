package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ЛОКАТОРЫ - элементы на главной странице

    // Кнопка "Заказать" вверху страницы
    private By topOrderButton = By.xpath("//button[contains(text(),'Заказать')]");

    // Кнопка "Заказать" внизу страницы
    private By bottomOrderButton = By.xpath("//div[@class='Home_FinishButton__1_cWm']//button[contains(text(),'Заказать')]");

    // Заголовок блока с вопросами
    private By faqTitle = By.xpath("//div[contains(text(),'Вопросы о важном')]");

    // Конструктор
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    // Кнопка-стрелка вопроса по индексу (0-7)
    private By faqQuestionButton(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Текст ответа на вопрос по индексу
    private By faqAnswerText(int index) {
        return By.xpath("//div[@id='accordion__panel-" + index + "']/p");
    }

    // Методы

    // Открыть главную страницу
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // Нажать на верхнюю кнопку "Заказать"
    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    // Нажать на нижнюю кнопку "Заказать" (с прокруткой до неё)
    public void clickBottomOrderButton() {
        // Сначала находим кнопку
        WebElement button = driver.findElement(bottomOrderButton);
        // Прокручиваем страницу до кнопки
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        // Ждём, когда кнопка станет кликабельной, и нажимаем
        wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton)).click();
    }

    // Нажать на вопрос в разделе "Вопросы о важном"
    public void clickFaqQuestion(int index) {
        // Ждём, пока вопрос станет доступен для клика
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(faqQuestionButton(index)));
        // Нажимаем на вопрос
        question.click();
    }

    // Получить текст ответа на вопрос
    public String getFaqAnswer(int index) {
        // Ждём, когда появится ответ, и сохраняем его
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(faqAnswerText(index)));
        // Возвращаем текст ответа
        return answer.getText();
    }

    // Проверить, загрузилась ли страница (по заголовку "Вопросы о важном")
    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(faqTitle)).isDisplayed();
    }

    // Дождаться полной загрузки страницы
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(faqTitle));
    }
}