package me.Bruno.TestCore.Tests;

import me.Bruno.TestCore.Driver.PlatformType;
import me.Bruno.TestCore.Driver.TestBase;
import me.Bruno.TestCore.PageActions.CalcDefaultActions;
import org.junit.Test;

public class DesktopTest extends TestBase {

    @Test
    public void teste() {
        new TestBase(PlatformType.WINIUM, "C:\\Windows\\System32\\calc.exe", "calculator");

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonMultiply();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonEquals();
    }

    @Test
    public void teste2() {
        new TestBase(PlatformType.WINIUM, "C:\\Windows\\System32\\calc.exe", "calculator");

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonMultiply();

        CalcDefaultActions.clickButtonNovo();

        CalcDefaultActions.clickButtonCinco();

        CalcDefaultActions.clickButtonUm();

        CalcDefaultActions.clickButtonEquals();
    }

}
