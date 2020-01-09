package me.Bruno.TestCore.Driver;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.KeyboardSimulatorType;
import org.openqa.selenium.winium.WiniumDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
public class TestBase extends Reporter {

	//https://www.techbeamers.com/generate-reports-selenium-webdriver/
	//https://www.rapidvaluesolutions.com/tech_blog/test-automation-winium/
	public static String logger;
	private static String driversPath = System.getProperty("user.dir") + "\\Drivers\\";
	public static String deployPath = System.getProperty("user.dir") + "\\Deploys\\";
	public static WebDriver driver;
	private static String custom;
	private static PlatformType type;
	private static String process;
	public static int timeOut = 25;
	private static int timeOut2 = timeOut;
	public static Actions actions;

	public TestBase() {}

	public TestBase(PlatformType Type, String customPath, String Process) {
		process = Process;
		new TestBase(Type, customPath);
	}

	@Before
	public void iniciar() {
		sleep(2000);

	}

	@After
	public void finalizar() {
		sleep(2000);
		try {
			if (type != PlatformType.WINIUM) {
				driver.quit();
			}
			driver = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		killProcess();
		sleep(2000);
	}

	public TestBase(PlatformType Type, String customPath) {
		if (type != null) {
			killProcess();
		}

		driver = null;
		custom = null;
		type = null;

		type = Type;
		custom = customPath;
		String pathOfDriver = getPath(type);
		if (!Arrays.asList(new PlatformType[] { PlatformType.ANDROID }).contains(type)) {
			System.setProperty(type.getProperty(), pathOfDriver);
		}
		DesiredCapabilities caps = new DesiredCapabilities();
		switch (type) {
		case WINIUM:
			kill("Winium.Desktop.Driver");
			sleep(200);
			DesktopOptions dOp = new DesktopOptions();
			dOp.setApplicationPath(customPath);
			dOp.setKeyboardSimulator(KeyboardSimulatorType.BasedOnWindowsFormsSendKeysClass);
			dOp.toCapabilities();
			run("Winium.Desktop.Driver");

			sleep(1000);
			try {
				driver = new WiniumDriver(new URL("http://localhost:9999"), dOp);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if (driver == null) new TestBase(type, custom);
			sleep(200);
			break;
		case ANDROID:
			sleep(500);
			JSONObject andrioid = read("AndroidConfig.json");
			for (Object i : andrioid.entrySet().toArray()) {
				String element = String.valueOf(i);
				caps.setCapability(element.split("=")[0], element.split("=")[1]);
			}
			try {
				driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if (driver == null) new TestBase(type, custom);
			break;
		case CHROME:
			kill("chromedriver");
			sleep(500);
			driver = new ChromeDriver();
			if (driver == null) new TestBase(type, custom);
			break;
		case EDGE:
			kill("MicrosoftWebDriver");
			sleep(500);
			driver = new EdgeDriver();
			if (driver == null) new TestBase(type, custom);
			break;
		case FIREFOX:
			kill("geckodriver");
			sleep(500);
			driver = new FirefoxDriver();
			if (driver == null) new TestBase(type, custom);
			break;
		case OPERA:
			kill("operadriver");
			ChromeOptions cOp = new ChromeOptions();
			if (customPath != null) {
				((ChromeOptions) cOp).setBinary(customPath);  
			}
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, cOp);
			driver = new OperaDriver(capabilities);
			if (driver == null) new TestBase(type, custom);
			break;
		default:
			break;
		}
		if (Arrays.asList(new PlatformType[] { PlatformType.OPERA, PlatformType.CHROME, PlatformType.EDGE, PlatformType.FIREFOX }).contains(type)) {
			driver.manage().timeouts().implicitlyWait(6l, TimeUnit.SECONDS);
		}
		if (driver == null) new TestBase(type, custom);
		actions = new Actions(driver);
	}

	private String getPath(PlatformType type) {
		switch (type) {
		case WINIUM:
			return driversPath + "Winium.Desktop.Driver.exe";
		case CHROME:
			return driversPath + "chromedriver.exe";
		case FIREFOX:
			return driversPath + "geckodriver.exe";
		case EDGE:
			return driversPath + "MicrosoftWebDriver.exe";
		default:
			break;
		}
		return null;
	}

	private static void createEvidence(String message, boolean condition) {
		if (alternateColor) {
			if (condition) {
				writeReport(message.replace("#color#", "escuro"). replace("#state#", "pass"));
			} else {
				writeReport(message.replace("#color#", "escuro"). replace("#state#", "fail"));
			}
			alternateColor = false;
		} else {
			if (condition) {
				writeReport(message.replace("#color#", "claro"). replace("#state#", "pass"));
			} else {
				writeReport(message.replace("#color#", "claro"). replace("#state#", "fail"));
			}
			alternateColor = true;
		}
		steps++;
	}

	private static String takePrint(WebElement e, Element frame) {
		if (type == PlatformType.WINIUM && e != null) {
			try {
				File full = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String attribute = null;
				if (frame == null) {
					return print();
				} else {
					try {
						attribute = frame.getElement().getAttribute("BoundingRectangle").replace("(", "").replace(")", "");
					} catch (Exception e2) {
						frame.reFindElement();
						if (timeOut2 > 1) {
							timeOut2--;
							return takePrint(e, frame);
						} else {
							return print();
						}
					}
				}
				int x = Integer.parseInt(attribute.split(",")[0]);
				int y = Integer.parseInt(attribute.split(",")[1]);
				int height = Integer.parseInt(attribute.split(",")[2]);
				int width = Integer.parseInt(attribute.split(",")[3]);
				BufferedImage crop = ImageIO.read(full).getSubimage(x, y, height, width);
				ImageIO.write(crop, "png", full);
				byte[] bytes = Files.readAllBytes(full.toPath());
				return Base64.encodeBase64String(bytes);
			} catch (IOException ex) {}
			return print();
		} else {
			return print();
		}
	}

	private static String print() {
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		} catch (AWTException e) {}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
			return java.util.Base64.getEncoder().encodeToString(os.toByteArray());
		} catch (IOException e) {
			return null;
		}
	}

	private static void kill(String process) {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM " + process + ".exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void run(String path) {
		try {
			Runtime.getRuntime().exec("cmd /c " + driversPath + File.separator + path + ".exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void killProcess() {
		if (type != null) {
			switch (type) {
				case WINIUM:
					kill("Winium.Desktop.Driver");
					if (process != null) {
						kill(process);
					}
					break;
				case CHROME:
					kill("chromedriver");
					break;
				case EDGE:
					kill("MicrosoftWebDriver");
					break;
				case FIREFOX:
					kill("geckodriver");
					break;
				case OPERA:
					kill("operadriver");
					break;
				default:
					break;
			}
		}
	}

	private static JSONObject read(String fileName) {
		String path = System.getProperty("user.dir") + "\\Files\\" + fileName;
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(path)) {
			return (JSONObject) parser.parse(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static FluentWait<WebDriver> getWait(int time, int every) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.withTimeout(time < 1 ? 60 : time, TimeUnit.SECONDS).pollingEvery(every < 0 ? 100 : every, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class).ignoring(NoAlertPresentException.class);
		wait.ignoring(ElementNotVisibleException.class).ignoring(ElementNotInteractableException.class);
		wait.ignoring(ElementNotSelectableException.class);
		return wait;
	}

	public static void setTimeOut(int timeOutSeconds) {
		timeOut = timeOutSeconds;
	}

	public static void checkPoint(boolean condition, WebElement element, String message) {
		checkPoint(condition, element, message, null);
	}

	public static void checkPoint(boolean condition, WebElement element, String message, Element frame) {
		timeOut2 = timeOut;
		String print = takePrint(element, frame);
		if (print != null) {
			String img = "<img src='data:image/jpg;base64, " + print + "' />";
			String step = logger != null ? "<h2 id='#state#'>" + logger + "</h2>" : "";
			String msg = message != null ? "<p>" + message + "</p>" : "";
			String toFile = "<div id='#color#'>" + step + msg + img + "</div>";
			if (Arrays.asList(new PlatformType[] { PlatformType.WINIUM, PlatformType.ANDROID }).contains(type)) {
				if (type == PlatformType.WINIUM) {
					createEvidence(toFile, condition);
				} else {
					createEvidence(toFile.replace(img, "<img style='height:580px' src='data:image/jpg;base64, " + print + "' />"), condition);
				}
			} else {
				createEvidence(toFile.replace(img, "<img style='width:100%;' src='data:image/jpg;base64, " + print + "' />"), condition);
			}
		}
		logger = null;
	}

	public static Alert getAlert() {
		Date future = new Date();
		future.setSeconds(future.getSeconds() + timeOut);
		do {
			Alert a = driver.switchTo().alert();
			if (a != null) {
				return a;
			} else {
				sleep(166);
			}
		} while (!new Date().after(future));
		return driver.switchTo().alert();
	}

	public static void sleep(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Robot getSimulator() {
		try {
			return new Robot();
		} catch (Exception e) {}
		return null;
	}

	public static boolean navigate(String url) {
		if (Arrays.asList(new PlatformType[] { PlatformType.OPERA, PlatformType.CHROME, PlatformType.EDGE, PlatformType.FIREFOX }).contains(type)) {
			driver.navigate().to(url);
			driver.manage().window().maximize();
			sleep(500);
			checkPoint(true, null, "");
			return true;
		}
		return false;
	}

	public static void waitPageContains(String message, int time) {
		By by = By.xpath("//*[contains(text(), '" + message + "')]");
		List<WebElement> list = getWait(time, 100).until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(by);
			}
		});
		if (list == null || list.isEmpty()) {
			Assert.fail("unable to find the message: " + message);
		}
	}

	public static void waitPageContainsElement(By by, int time) {
		List<WebElement> list = getWait(time, 100).until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(by);
			}
		});
		if (list == null || list.isEmpty()) {
			Assert.fail("unable to find the element: " + by);
		}
	}

	public static Element getElement(By by) {
		try {
			return new Element(by, getWait(30, 100).until(new Function<WebDriver, WebElement> () {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			}));
		} catch (Exception e) {
			Assert.fail("unable to find the element: " + by);
			return null;
		}
	}

	public static ArrayList<Element> getElements(By by) {
		try {
			ArrayList<Element> list = new ArrayList<>();
			int attempts = 5;
			do {
				List<WebElement> els = getWait(30, 100).until(new Function<WebDriver, List<WebElement>>() {
					public List<WebElement> apply(WebDriver driver) {
						return driver.findElements(by);
					}
				});
				if (els != null && els.size() > 0) {
					for (WebElement e : els) {
						if (e != null && e.isDisplayed()) {
							for (WebElement el : els) {
								list.add(new Element(by, el));
							}
							break;
						}
					}
				} else {
					attempts--;
					if (attempts <= 0) {
						break;
					}
				}
			} while (list.isEmpty());
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	// public static Element getElement(By by) {
	// 	Date future = new Date();
	// 	future.setSeconds(future.getSeconds() + timeOut);

	// 	do {
	// 		try {
	// 			WebElement e = driver.findElement(by);
	// 			if (e != null && e.isEnabled()) {
	// 				return new Element(by, e);
	// 			} else {
	// 				sleep(166);
	// 			}
	// 		} catch (NoSuchElementException e) {
	// 			sleep(166);
	// 		}
	// 	} while (!new Date().after(future));
	// 	System.out.println("could not find locator element: " + by);
	// 	return null;
	// }

	// public static ArrayList<Element> getElements(By by) {
	// 	ArrayList<Element> list = new ArrayList<>();
	// 	Date future = new Date();
	// 	future.setSeconds(future.getSeconds() + timeOut);

	// 	do {
	// 		try {
	// 			List<WebElement> elements = driver.findElements(by);
	// 			if (elements != null && elements.size() > 0) {
	// 				for (WebElement e : elements) {
	// 					if (e != null && e.isEnabled()) {
	// 						for (WebElement e1 : elements) {
	// 							list.add(new Element(by, e1));
	// 						}
	// 					}
	// 				}
	// 				return list;
	// 			} else {
	// 				sleep(166);
	// 			}
	// 		} catch (NoSuchElementException e) {
	// 			System.out.println("TESTE");
	// 			sleep(166);
	// 		}
	// 	} while (!new Date().after(future));
	// 	System.out.println("could not find locator element: " + by);
	// 	return null;
	// }

}
