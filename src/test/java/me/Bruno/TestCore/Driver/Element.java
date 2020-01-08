package me.Bruno.TestCore.Driver;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class Element {

    private By by;
    private WebElement element;
    private int attempts = 40;
    private boolean fail = true;

    public Element(By by) {
        this.by = by;
    }

    public Element(By by, WebElement element) {
        this.by = by;
        this.element = element;
    }

    public WebElement getElement() {
        return this.element;
    }

    public By getBy() {
        return this.by;
    }

    public boolean click() {
        return click(null);
    }

    public boolean click(Element frame) {
        if (element != null) {
            try {
                element.click();
                TestBase.sleep(250);
                TestBase.checkPoint(true, element, "Click on Element: (" + element + ")", frame);
                return true;
            } catch (StaleElementReferenceException e) {
                if (attempts < 1) {
                    TestBase.checkPoint(false, element, "Unable to click element: (" + element + ")", frame);
                    return false;
                } else {
                    attempts--;
                    reFindElement();
                    click(frame);
                    TestBase.sleep(250);
                    return false;
                }
            }
        } else {
            if (fail) {
                Assert.fail("unable to click on element: " + by);
            }
        }
        return false;
    }

    public boolean clear() {
        return clear(null);
    }

    public boolean clear(Element frame) {
        if (element != null) {
            try {
                element.clear();
                TestBase.sleep(250);
                TestBase.checkPoint(true, element, "Clear on Element: (" + element + ")", frame);
                return true;
            } catch (Exception e) {
                if (attempts < 1) {
                    TestBase.checkPoint(false, element, "Could not clear element: (" + element + ")", frame);
                    return false;
                } else {
                    attempts--;
                    reFindElement();
                    clear(frame);
                    TestBase.sleep(1000);
                    return false;
                }
            }
        } else {
            if (fail) {
                Assert.fail("unable to clear on element: " + by);
            }
        }
        return false;
    }

    public boolean sendKeys(String message) {
        return sendKeys(message, null);
    }

    public boolean sendKeys(String message, Element frame) {
        if (element != null) {
            try {
                element.sendKeys(message);
                TestBase.sleep(250);
                TestBase.checkPoint(true, element, "SendKeys on Element: (" + element + ")", frame);
                return true;
            } catch (Exception e) {
                if (attempts < 1) {
                    TestBase.checkPoint(false, element, "Could not perform SendKeys on element: (" + element + ")", frame);
                    return false;
                } else {
                    attempts--;
                    reFindElement();
                    sendKeys(message, frame);
                    TestBase.sleep(1000);
                    return false;
                }
            }
        } else {
            if (fail) {
                Assert.fail("unable to sendkeys on element: " + by);
            }
        }
        return false;
    }

    public boolean doubleClick() {
        return doubleClick(null);
    }

    public boolean doubleClick(Element frame) {
        if (element != null) {
            try {
                TestBase.actions.doubleClick(element).perform();
                TestBase.sleep(250);
                TestBase.checkPoint(true, element, "Double Click on Element: (" + element + ")", frame);
                return true;
            } catch (Exception e) {
                if (attempts < 1) {
                    TestBase.checkPoint(false, element, "Could not perform Double Click on element: (" + element + ")", frame);
                    return false;
                } else {
                    attempts--;
                    reFindElement();
                    doubleClick(frame);
                    TestBase.sleep(1000);
                    return false;
                }
            }
        } else {
            if (fail) {
                Assert.fail("unable to doubleclick on element: " + by);
            }
        }
        return false;
    }

    public boolean rightClick() {
        return rightClick(null);
    }

    public boolean rightClick(Element frame) {
        if (element != null) {
            try {
                TestBase.actions.contextClick(element).perform();
                TestBase.sleep(250);
                TestBase.checkPoint(true, element, "Right Click on Element: (" + element + ")", frame);
                return true;
            } catch (Exception e) {
                if (attempts < 1) {
                    TestBase.checkPoint(false, element, "Could not perform Right Click on element: (" + element + ")", frame);
                    return false;
                } else {
                    attempts--;
                    reFindElement();
                    rightClick(frame);
                    TestBase.sleep(1000);
                    return false;
                }
            }
        } else {
            if (fail) {
                Assert.fail("unable to rightclick on element: " + by);
            }
        }
        return false;
    }

    public boolean remove() {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) TestBase.driver;
            js.executeScript("arguments[0].remove()", element);
            return true;
        } else {
            if (fail) {
                Assert.fail("unable to remove element: " + by);
            }
            return false;
        }
    }

    public boolean show() {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) TestBase.driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
            return true;
        } else {
            if (fail) {
                Assert.fail("unable to scrollIntoView element: " + by);
            }
            return false;
        }
    }

    public boolean hidden() {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) TestBase.driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, "style", "visibility: hidden;");
            return true;
        } else {
            if (fail) {
                Assert.fail("unable to hidden element: " + by);
            }
            return false;
        }
    }

    public boolean setAttribute(String attributeName, String attributeValue) {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) TestBase.driver;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
            return true;
        } else {
            if (fail) {
                Assert.fail("unable to setAttribute element: " + by);
            }
            return false;
        }
    }

    public void reFindElement() {
        this.element = TestBase.getElement(this.by).getElement();
    }

    public boolean exist() {
        return (element != null && element.isDisplayed());
    }

    public Element fail(boolean value) {
        this.fail = value;
        return this;
    }

}
