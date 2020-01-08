package me.Bruno.TestCore.PageActions;

import me.Bruno.TestCore.Driver.Element;
import me.Bruno.TestCore.Driver.TestBase;
import me.Bruno.TestCore.PageElements.CalcDefaultElements;
import org.openqa.selenium.By;

public class CalcDefaultActions extends TestBase {

    public static Element frame = getElement(By.className("Windows.UI.Core.CoreWindow"));

    public static void clickButtonZero() {
        logger = "Click button 0";
        getElement(CalcDefaultElements.zero).click(frame);
    }

    public static void clickButtonUm() {
        logger = "Click button 1";
        getElement(CalcDefaultElements.um).click(frame);
    }

    public static void clickButtonDois() {
        logger = "Click button 2";
        getElement(CalcDefaultElements.dois).click(frame);
    }

    public static void clickButtonTres() {
        logger = "Click button 3";
        getElement(CalcDefaultElements.tres).click(frame);
    }

    public static void clickButtonQuatro() {
        logger = "Click button 4";
        getElement(CalcDefaultElements.quatro).click(frame);
    }

    public static void clickButtonCinco() {
        logger = "Click button 5";
        getElement(CalcDefaultElements.cinco).click(frame);
    }

    public static void clickButtonSeis() {
        logger = "Click button 6";
        getElement(CalcDefaultElements.seis).click(frame);
    }

    public static void clickButtonSete() {
        logger = "Click button 7";
        getElement(CalcDefaultElements.sete).click(frame);
    }

    public static void clickButtonOito() {
        logger = "Click button 8";
        getElement(CalcDefaultElements.oito).click(frame);
    }

    public static void clickButtonNovo() {
        logger = "Click button 9";
        getElement(CalcDefaultElements.nove).click(frame);
    }

    public static void clickButtonPlus() {
        logger = "Click button +";
        getElement(CalcDefaultElements.plus).click(frame);
    }

    public static void clickButtonMinus() {
        logger = "Click button -";
        getElement(CalcDefaultElements.minus).click(frame);
    }

    public static void clickButtonDivide() {
        logger = "Click button /";
        getElement(CalcDefaultElements.divide).click(frame);
    }

    public static void clickButtonMultiply() {
        logger = "Click button *";
        getElement(CalcDefaultElements.multiply).click(frame);
    }

    public static void clickButtonEquals() {
        logger = "Click button =";
        getElement(CalcDefaultElements.equals).click(frame);
    }

    public static void clickButtonBackSpace() {
        logger = "Click button <-";
        getElement(CalcDefaultElements.backspace).click(frame);
    }

    public static void clickButtonClear() {
        logger = "Click button (Clear)";
        getElement(CalcDefaultElements.clear).click(frame);
    }

}
