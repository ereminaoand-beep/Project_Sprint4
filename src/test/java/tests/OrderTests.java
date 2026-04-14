package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.OrderPage;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderTests {

    private WebDriver driver;
    private HomePage homePage;
    private OrderPage orderPage;

    // Данные для заказа
    private String buttonPosition;  // "top" или "bottom"
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String date;
    private String rentalPeriod;
    private String comment;

    // Конструктор
    public OrderTests(String buttonPosition, String firstName, String lastName,
                      String address, String metroStation, String phone,
                      String date, String rentalPeriod, String comment) {
        this.buttonPosition = buttonPosition;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.comment = comment;
    }

    // ПАРАМЕТРИЗАЦИЯ - два набора данных: верхняя и нижняя кнопка
    @Parameterized.Parameters(name = "Кнопка: {0}, Имя: {1}")
    public static Object[][] getData() {
        return new Object[][] {
                // Набор 1: Верхняя кнопка + данные Ивана
                {"top", "Иван", "Петров", "ул. Ленина 1", "Сокольники",
                        "89991234567", "05.12.2024", "сутки", "Позвоните за час"},

                // Набор 2: Нижняя кнопка + данные Марии
                {"bottom", "Мария", "Иванова", "пр. Мира 10", "Кропоткинская",
                        "89887654321", "10.12.2024", "трое суток", "Домофон 123"}
        };
    }

    // Что делаем перед каждым тестом
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        orderPage = new OrderPage(driver);

        homePage.open();
        homePage.isPageLoaded();
    }

    // Сам тест
    @Test
    public void testCreateOrder() {
        // Нажимаем нужную кнопку заказа
        if (buttonPosition.equals("top")) {
            homePage.clickTopOrderButton();
        } else {
            homePage.clickBottomOrderButton();
        }

        // Создаём заказ и проверяем результат
        boolean success = orderPage.createOrder(
                firstName, lastName, address, metroStation, phone,
                date, rentalPeriod, comment
        );

        assertTrue("Заказ не создан", success);
    }

    // Закрыть браузер
    @After
    public void tearDown() {
            driver.quit();
    }
}