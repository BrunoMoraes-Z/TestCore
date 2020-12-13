package com.bruno.testwork;

import com.bruno.testwork.framework.TestType;
import com.bruno.testwork.framework.Utils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.KeyboardSimulatorType;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public abstract class TestBase {

    private TestType type;
    private WebDriver driver;
    private HashMap<TestType, WebDriver> drivers;
    private String path, deployPath, process, applicationPath;
    private Actions actions;

    public TestBase() {
        this.drivers = new HashMap<>();
        this.deployPath = System.getProperty("user.dir") + "\\deploys\\";
        this.path = Utils.getProperty("pathDriver");
    }

    public void setDriverType(TestType type) {
        this.setDriverType(type, false);
    }

    public TestBase setApplicationPath(String process, String applicationPath) {
        this.process = process;
        this.applicationPath = applicationPath;
        return this;
    }

    public void setDriverType(TestType type, boolean defaultSite) {
        if (this.type != null && this.driver != null) {
            this.drivers.put(this.type, this.driver);
        }
        if (this.drivers.containsKey(type)) {
            this.type = type;
            this.driver = this.drivers.get(type);
            return;
        }
        this.type = type;
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
            case DEKSTOP_WINIUM:
                if (this.applicationPath == null) {
                    System.out.println("Nenhum Aplicativo Informado. Utilize setApplicationPath(<path>)");
                    break;
                }
                System.setProperty("webdriver.winium.driver.desktop", path.concat("winiumdriver.exe"));
                DesktopOptions d_op = new DesktopOptions();
                d_op.setApplicationPath(this.applicationPath);
                d_op.setKeyboardSimulator(KeyboardSimulatorType.BasedOnWindowsFormsSendKeysClass);
                d_op.toCapabilities();
                startApplication(path.concat("winiumdriver.exe"));
                try {
                    driver = new WiniumDriver(new URL("http://localhost:9999"), d_op);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
        }

        if (TestType.isWeb(type) && driver != null) {
            if (Boolean.parseBoolean(Utils.getProperty("maximize"))) {
                driver.manage().window().maximize();
            }
            if (defaultSite) {
                driver.get(Utils.getProperty("URL"));
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            this.actions = new Actions(this.driver);
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    protected void setup() {
        System.out.println("Iniciando...");
    }

    @AfterSuite
    protected void teardown() {
        System.out.println("Finalizando...");
        sleep(1000);
        killTask("chromedriver.exe");
        killTask("geckdriver.exe");
        killTask("msedgedriver.exe");
        killTask("operadriver.exe");
        killTask("winiumdriver.exe");
        if (this.process != null) {
            killTask(process);
        }
    }

    public void killTask(String executable) {
        try {
            Runtime.getRuntime().exec("taskkill /f /im " + executable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startApplication(String appPath) {
        try {
            Runtime.getRuntime().exec("cmd /c " + appPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @BeforeSuite
    public void suiteSetup() {
    }

    @BeforeMethod
    public void testSetup() {
    }

    @AfterSuite
    public void suiteTeardown() {
    }

    @AfterMethod
    public void testTeardown() {
    }


}
