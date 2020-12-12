package com.bruno.testwork;

import com.bruno.testwork.framework.TestType;
import com.bruno.testwork.framework.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.*;

public abstract class TestBase {

    private static TestType type = TestType.WEB_EDGE;

    public TestBase() {
        String path = Utils.getProperty("pathDriver");
        WebDriver driver = null;

        switch (type) {
            case WEB_CHROME:
                System.setProperty(type.getDriverName(), path.concat("chromedriver.exe"));
                driver = new ChromeDriver();
                break;
            case WEB_FIREFOX:
                System.setProperty(type.getDriverName(), path.concat("geckodriver.exe"));
                driver = new FirefoxDriver();
                break;
            case WEB_OPERA:
                System.setProperty(type.getDriverName(), path.concat("operadriver.exe"));
                driver = new OperaDriver();
                break;
            case WEB_EDGE:
                System.setProperty("webdriver.edge.driver", path.concat("msedgedriver.exe"));
                EdgeOptions e_op = new EdgeOptions();
                e_op.setCapability("chromium", true);
                driver = new EdgeDriver(e_op);
                break;
        }

        if (TestType.isWeb(type) && driver != null) {
            if (Boolean.parseBoolean(Utils.getProperty("maximize"))) {
                driver.manage().window().maximize();
            }
            driver.get(Utils.getProperty("URL"));
        }
    }

    @BeforeSuite
    public void suiteSetup() {
        System.out.println("suite setup");
    }

    @BeforeMethod
    public void testSetup() {
        System.out.println("test setup");
    }

    @AfterSuite
    public void suiteTeardown() {
        System.out.println("suite teardown");
    }

    @AfterMethod
    public void testTeardown() {
        System.out.println("test teardown");
    }


}
