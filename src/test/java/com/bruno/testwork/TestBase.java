package com.bruno.testwork;

import com.bruno.testwork.framework.TestType;
import com.bruno.testwork.framework.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class TestBase {

    public static void main(String[] args) {
        String path = Utils.getProperty("pathDriver");

        WebDriver driver = null;

        TestType type = TestType.WEB_EDGE;

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

        if (TestType.isWeb(type)) {
            if (Boolean.parseBoolean(Utils.getProperty("maximize"))) {
                driver.manage().window().maximize();
            }
            driver.get(Utils.getProperty("URL"));
        }
    }

}
