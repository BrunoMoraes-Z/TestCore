package com.bruno.testwork.suites;

import com.bruno.testwork.TestBase;
import com.bruno.testwork.framework.Element;
import com.bruno.testwork.framework.LocatorType;
import com.bruno.testwork.framework.TestType;
import com.bruno.testwork.framework.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class teste extends TestBase {

//    @Test
//    public void teste01() {
//        setApplicationPath("Calculator.exe", "C:/Windows/System32/calc.exe").setDriverType(TestType.DEKSTOP_WINIUM);
//
//        Element btn_1 = new Element(LocatorType.ID, "num1Button");
//        Element btn_5 = new Element(LocatorType.ID, "num5Button");
//        Element btn_9 = new Element(LocatorType.ID, "num9Button");
//        btn_1.click(4);
//        btn_5.click();
//        btn_9.click();
//        btn_5.click();
//        sleep(2000);
//    }

    @Test
    public void teste02() {
//        Ele vai abrir o chrome, acessar o google e pesquisar mercado livre e apertar um enter para efetivar a pesquisa.
        setDriverType(TestType.WEB_CHROME);
        getDriver().get("https://google.com");
        new Element(LocatorType.NAME, "q").input("Mercado Livre").enter();
    }

    @Test
    public void teste03() {
//        setDriverType(TestType.WEB_EDGE);
//        getDriver().get("https://www.branitube.net/watch/30979/quanzhi-gaoshou-2");
    }

}
