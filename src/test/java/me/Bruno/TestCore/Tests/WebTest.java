package me.Bruno.TestCore.Tests;

import me.Bruno.TestCore.Driver.Element;
import me.Bruno.TestCore.Driver.Utils;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import me.Bruno.TestCore.Driver.PlatformType;
import me.Bruno.TestCore.Driver.TestBase;

import java.util.Iterator;
import java.util.Set;

public class WebTest extends TestBase {

	@Test
	public void chrome_search() {
		new TestBase(PlatformType.CHROME, null);
        navigate("https://google.com");
        waitPageContains("Google", 5);
        Element search = getElement(By.name("q"));
        search.sendKeys("amazon" + Keys.RETURN);
    }

    @Test
    public void alertTest() {
	    new TestBase(PlatformType.CHROME, null);
	    navigate("http://demo.guru99.com/test/delete_customer.php");
	    getElement(By.name("cusid")).sendKeys("53920");
	    getElement(By.name("submit")).click();
        Alert alert = getAlert();
        System.out.println(alert.getText());
        alert.accept();
        System.out.println(getAlert().getText());
    }

    //@Test
    public void popUpTest() {
        new TestBase(PlatformType.CHROME, null);
        navigate("http://demo.guru99.com/popup.php");
        getElement(By.xpath("//*[contains(@href,'popup.php')]")).click();

        String win = driver.getWindowHandle();
        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();

        while (i1.hasNext()) {
            String cild = i1.next();
            if (!win.equalsIgnoreCase(cild)) {
                driver.switchTo().window(cild);
                getElement(By.name("emailid")).sendKeys("teste@mailinator.com");
                getElement(By.name("btnLogin")).click();
                driver.close();
            }
        }
        driver.switchTo().window(win);
    }

    @Test
    public void iFrameTest() {
        new TestBase(PlatformType.CHROME, null);
        navigate("http://demo.guru99.com/test/guru99home/");
        driver.switchTo().frame("a077aa5e");
        System.out.println("********We are switch to the iframe*******");
        getElement(By.xpath("html/body/a/img")).click();
        System.out.println("*********We are done***************");
    }

    //@Test
    public void iFrameTest2() {
        new TestBase(PlatformType.CHROME, null);
        navigate("http://demo.guru99.com/test/guru99home/");
        int size = getElements(By.tagName("iframe")).size();
        for(int i=0; i<=size; i++){
            try {
                driver.switchTo().frame(i);
                int total = getElements(By.xpath("html/body/a/img")).size();
                System.out.println(total);
                driver.switchTo().defaultContent();
            } catch (Exception e) {

            }
        }
    }

	//@Test
	public void mercadao_firefox() {
		new TestBase(PlatformType.FIREFOX, null);
        navigate("https://google.com");
        Element search = getElement(By.name("q"));
        search.sendKeys("mercado livre" + Keys.RETURN);
    }

	//@Test
	public void mercadao_edge() {
		new TestBase(PlatformType.EDGE, null);
        navigate("https://google.com");
        Element search = getElement(By.name("q"));
        search.sendKeys("mercado livre" + Keys.RETURN);
	}
	
}
