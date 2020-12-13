package com.bruno.testwork.suites;

import com.bruno.testwork.TestBase;
import com.bruno.testwork.framework.TestType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class teste extends TestBase {

    @Test
    public void teste01() {
        setApplicationPath("Calculator.exe", "C:/Windows/System32/calc.exe").setDriverType(TestType.DEKSTOP_WINIUM);

        By um = By.id("num1Button");
        getDriver().findElement(um).click();
        getDriver().findElement(um).click();
        getDriver().findElement(um).click();
        sleep(2000);
    }

//    @Test
//    public void teste02() {
//        setDriverType(TestType.WEB_CHROME);
//        getDriver().get("https://google.com");
//    }
//
//    @Test
//    public void teste03() {
//        setDriverType(TestType.WEB_EDGE);
//        getDriver().get("https://www.branitube.net/watch/30979/quanzhi-gaoshou-2");
//    }

}
