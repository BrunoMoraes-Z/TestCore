package com.bruno.testwork.framework;

import com.bruno.testwork.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Element {

    private String locator;
    private By seleniumLocator;
    private WebElement seleniumElement;
    private LocatorType type;
    private int timeout = 60;

    public Element(LocatorType type, String locator) {
        this(type, locator, 60);
    }

    public Element(LocatorType type, String locator, int timeout) {
        this.type = type;
        this.locator = locator;
        this.timeout = timeout;
        switch (type) {
            case ID:
                this.seleniumLocator = By.id(locator);
                break;
            case CSS:
                this.seleniumLocator = By.cssSelector(locator);
                break;
            case CLASS:
                this.seleniumLocator = By.className(locator);
                break;
            case XPATH:
                this.seleniumLocator = By.xpath(locator);
                break;
            case NAME:
                this.seleniumLocator = By.name(locator);
                break;
            case TAG:
                this.seleniumLocator = By.tagName(locator);
                break;
        }
        if (this.type == LocatorType.IMAGE) {
            ArrayList<File> paths = TestBase.getImagePaths();
            ArrayList<File> finds = new ArrayList<>();
            String finalLocator = this.locator;
            paths.forEach(d -> {
                File[] fls = d.listFiles((dir, name) -> name.equalsIgnoreCase(finalLocator));
                assert fls != null;
                if (fls.length > 0) {
                    finds.addAll(Arrays.asList(fls));
                }
            });
            if (finds.size() > 1) {
                this.locator = null;
                System.out.println("Mais de um elemento encontrado com mesmo nome.");
                finds.forEach(f -> System.out.println(f.getPath()));
            } else {
                this.locator = finds.get(0).getPath();
            }
        }
        find();
    }

    private void find() {
        WebDriver driver = TestBase.getDriver();
        TestType driverType = TestBase.getTestType();
        FluentWait<WebDriver> waiter = TestBase.getWaiter(timeout, 100);
        switch (driverType) {
            case WEB_CHROME:
            case WEB_FIREFOX:
            case WEB_OPERA:
            case WEB_EDGE:
            case DEKSTOP_WINIUM:
            case DEKSTOP_APPIUM:
            case ANDROID:
            case IOS:
                this.seleniumElement = getElement(seleniumLocator, waiter);
                break;
            case DESKTOP_AUTOIT:
                break;
            case DESKTOP_SIKULI:
                break;
        }
    }

    public String getLocator() {
        return locator;
    }

    public LocatorType getType() {
        return type;
    }

    public int getTimeout() {
        return timeout;
    }

    private WebElement getElement(By element, FluentWait<WebDriver> waiter) {
        return waiter.until(webDriver -> webDriver.findElement(element));
    }

    public WebElement getSeleniumElement() {
        return seleniumElement;
    }

    public InputElement input(String message) {
        getSeleniumElement().sendKeys(message);
        return new InputElement(this);
    }

    public InputElement inputEnd(String message) {
        getSeleniumElement().sendKeys(Keys.END + message);
        return new InputElement(this);
    }

    public InputElement inputHome(String message) {
        getSeleniumElement().sendKeys(Keys.HOME + message);
        return new InputElement(this);
    }

    public void pressKeys(Keys... keys) {
        Arrays.asList(keys).forEach(k -> getSeleniumElement().sendKeys(k));
    }

    public void clear() {
        getSeleniumElement().clear();
    }

    public void click() {
        click(1);
    }

    public void click(int repeats) {
        for (int i = 0; i < repeats; i++) {
            getSeleniumElement().click();
        }
    }


    public class InputElement extends Element {

        Element element;

        public InputElement(Element element) {
            super(element.getType(), element.getLocator(), element.getTimeout());
            this.element = element;
        }

        public InputElement(LocatorType type, String locator, int timeout) {
            super(type, locator, timeout);
        }

        public void tab() {
            element.getSeleniumElement().sendKeys(Keys.TAB);
        }

        public void enter() {
            element.getSeleniumElement().sendKeys(Keys.ENTER);
        }

        public void home() {
            element.getSeleniumElement().sendKeys(Keys.HOME);
        }

        public void delete() {
            element.getSeleniumElement().sendKeys(Keys.DELETE);
        }

        public void backSpace() {
            element.getSeleniumElement().sendKeys(Keys.BACK_SPACE);
        }

        public void arrowLeft() {
            element.getSeleniumElement().sendKeys(Keys.ARROW_LEFT);
        }

        public void arrowRight() {
            element.getSeleniumElement().sendKeys(Keys.ARROW_RIGHT);
        }

        public void arrowDown() {
            element.getSeleniumElement().sendKeys(Keys.ARROW_DOWN);
        }

        public void arrowUp() {
            element.getSeleniumElement().sendKeys(Keys.ARROW_UP);
        }

    }

}
