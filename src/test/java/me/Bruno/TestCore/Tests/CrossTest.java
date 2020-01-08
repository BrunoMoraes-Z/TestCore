package me.Bruno.TestCore.Tests;

import me.Bruno.TestCore.Driver.Element;
import me.Bruno.TestCore.Driver.PlatformType;
import me.Bruno.TestCore.Driver.TestBase;
import me.Bruno.TestCore.PageActions.CalcDefaultActions;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class CrossTest extends TestBase {

    @Test
    public void CrossPlatform() {
        //Desktop
        new TestBase(PlatformType.WINIUM, "C:\\Windows\\System32\\calc.exe", "calculator");

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonMultiply();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonEquals();

        //Android

        new TestBase(PlatformType.ANDROID, null);

        Element button = getElement(By.id("com.google.android.calculator:id/digit_1"));

        button.click();

        //Web

        new TestBase(PlatformType.CHROME, null);

        navigate("https://google.com");

        Element search = getElement(By.name("q"));

        search.sendKeys("amazon" + Keys.RETURN);
    }

}
