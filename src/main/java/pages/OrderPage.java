package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы

    // Первая страница заказа - данные пользователя
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationField = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    // Вторая страница - данные заказа
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-placeholder");
    private By blackColorCheckbox = By.id("black");
    private By greyColorCheckbox = By.id("grey");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[contains(@class,'Button_Button__ra12g') and text()='Заказать']");
    private By confirmYesButton = By.xpath("//button[text()='Да']");
    private By successMessage = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    // Конструктор
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }



    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
    }

    public void enterSurname(String surname) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(surnameField)).sendKeys(surname);
    }

    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressField)).sendKeys(address);
    }

    public void selectMetroStation(String stationName) {
        wait.until(ExpectedConditions.elementToBeClickable(metroStationField)).click();
        By stationOption = By.xpath("//div[contains(@class,'Order_SelectOption__') and text()='" + stationName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(stationOption)).click();
    }

    public void enterPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField)).sendKeys(phone);
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }


    public void enterDate(String date) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField)).sendKeys(date);
        // Нажимаем Enter, чтобы закрыть календарь
        driver.findElement(dateField).submit();
    }

    public void selectRentalPeriod(String period) {
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField)).click();
        By periodOption = By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + period + "']");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption)).click();
    }

    public void selectColor(String color) {
        if (color.equalsIgnoreCase("черный")) {
            wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox)).click();
        } else if (color.equalsIgnoreCase("серый")) {
            wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox)).click();
        }
    }

    public void enterComment(String comment) {
        if (comment != null && !comment.isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(commentField)).sendKeys(comment);
        }
    }

    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    public boolean isOrderSuccessfullyCreated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    // ===== ГЛАВНЫЙ МЕТОД ДЛЯ СОЗДАНИЯ ЗАКАЗА (объединяет все шаги) =====
    public boolean createOrder(String firstName, String lastName, String address,
                               String metroStation, String phone, String date,
                               String rentalPeriod, String comment) {
        // Заполняем первую страницу
        enterName(firstName);
        enterSurname(lastName);
        enterAddress(address);
        selectMetroStation(metroStation);
        enterPhone(phone);
        clickNextButton();

        // Заполняем вторую страницу
        enterDate(date);
        selectRentalPeriod(rentalPeriod);
        selectColor("черный");  // Можно передавать цвет параметром
        enterComment(comment);
        clickOrderButton();
        confirmOrder();

        // Возвращаем результат (успешно или нет)
        return isOrderSuccessfullyCreated();
    }
}