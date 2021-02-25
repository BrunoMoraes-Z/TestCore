package com.bruno.testwork;

import com.microsoft.appcenter.appium.EnhancedAndroidDriver;
import com.microsoft.appcenter.appium.Factory;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Test_temp {

    @Rule
    public TestWatcher watcher = Factory.createWatcher();

    private static EnhancedAndroidDriver<MobileElement> driver;

    //mvn -DskipTests -P prepare-for-upload package
    //appcenter test run appium --app "BrunoMoraes/GitExplorer" --devices 3d5bb2cf --app-path app-release.apk --test-series "master" --locale "en_US" --build-dir target/upload
    @Test
    public void test() throws InterruptedException {

        DesiredCapabilities caps = new DesiredCapabilities();

//        caps.setCapability("platformName", "Android");
//        caps.setCapability("deviceName", "ZY222TLSKC");
        caps.setCapability("appPackage", "com.example.git_explorer");
        caps.setCapability("appActivity", "com.example.git_explorer.MainActivity");
//        caps.setCapability("autoGrantPermissions", true);
//        caps.setCapability("printPageSourceOnFindFailure", true);
//        caps.setCapability("adbExecTimeout", 60000);
//        caps.setCapability("newCommandTimeout", 0);

        driver = Factory.createAndroidDriver(caps);

        Thread.sleep(10*1000);
        MobileElement element = driver.findElement(By.xpath("//android.widget.EditText[contains(@text, 'reposi')]"));
        element = driver.findElement(By.xpath("//android.widget.EditText[contains(@text, 'reposi')]/android.widget.Button"));
        Thread.sleep(3*1000);
    }

    @After
    public void TearDown(){
        driver.label("Stopping App");
        driver.quit();
    }

}
