package me.Bruno.TestCore.Tests;

import me.Bruno.TestCore.Driver.Element;
import me.Bruno.TestCore.Driver.PlatformType;
import me.Bruno.TestCore.Driver.TestBase;
import org.junit.Test;
import org.openqa.selenium.By;

public class MobileTest extends TestBase {

    @Test
    public void calculadora_Android() {
        new TestBase(PlatformType.ANDROID, null);
        Element button = getElement(By.id("com.google.android.calculator:id/digit_1"));
        button.click();
    }

}
