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


    // Прокрутить страницу до элемента
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



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
        WebElement button = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(bottomOrderButton)).click();
    }

    // Нажать на вопрос в разделе "Вопросы о важном"
    public void clickFaqQuestion(int index) {
        // Находим элемент вопроса
        WebElement question = driver.findElement(faqQuestionButton(index));

        // Прокручиваем до вопроса (чтобы он точно был виден)
        scrollToElement(question);

        // Ждём, пока вопрос станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(question));

        // Пробуем нажать обычным способом
        try {
            question.click();
        } catch (Exception e) {
            // Если обычный клик не сработал - нажимаем через JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
        }
    }

    // Получить текст ответа на вопрос
    public String getFaqAnswer(int index) {
        // Ждём, когда появится ответ
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(faqAnswerText(index)));
        // Прокручиваем к ответу
        scrollToElement(answer);
        // Возвращаем текст
        return answer.getText();
    }

    // Проверить, загрузилась ли страница
    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(faqTitle)).isDisplayed();
    }

    // Дождаться полной загрузки страницы
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(faqTitle));
    }
}